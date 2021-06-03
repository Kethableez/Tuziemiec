import { Component, OnInit } from '@angular/core';
import { Trip } from '../../_model/trip';
import { User } from '../../_model/user';
import { TokenStorageService } from '../../_services/token-storage.service';
import { TripService } from '../../_services/trip.service';
import { UserService } from '../../_services/user.service';
import { FormBuilder, Validators  } from '@angular/forms';
import { isPast } from 'date-fns';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  isLoggedIn = false;
  currentUser: any;
  userData: User;
  failedRegister = false;
  errorMessage: string;
  isSubmit = false;

  isDatePast = false;
  isTried = false;

  constructor(
    private token: TokenStorageService, 
    private userService: UserService, 
    private fb: FormBuilder) { }

    editPersonalDataForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      dayOfBirth: ['', Validators.required]
  });

  get dayOfBirth() {
    return this.editPersonalDataForm.get('dayOfBirth');
  }

  get firstName(){
    return this.editPersonalDataForm.get('firstName');
  }

  get lastName() {
    return this.editPersonalDataForm.get('lastName');
  }

  onSubmit(){
    console.log(typeof(this.userData.dayOfBirth));
    console.log(this.editPersonalDataForm.value);
    this.chceckDate();
    if (this.isDatePast){
      this.userService.editData(this.editPersonalDataForm.value).subscribe(
        response => console.log('Success!', response),
        err => {
            this.errorMessage = err.error.message;
        }
    );

    this.isSubmit = true;
    }

}

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }
  
    if (this.isLoggedIn == true){
      this.userService.getData().subscribe(
        (response: User) => {
          this.userData = response;
        }
      )    
    }
  }

  logout(): void {
    this.token.signOut();
  }

  reloadPage(): void {
    window.location.reload();
  }

  chceckDate() : void{
    this.isDatePast = isPast(new Date(this.dayOfBirth.value));
    this.isTried = true;
}
}
