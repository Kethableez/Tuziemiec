import { Component, OnInit } from '@angular/core';
import { Trip } from '../_model/trip';
import { User } from '../_model/user';
import { TokenStorageService } from '../_services/token-storage.service';
import { TripService } from '../_services/trip.service';
import { UserService } from '../_services/user.service';
import { FormBuilder, Validators  } from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  isLoggedIn = false;
  isEditPanelClicked = false;
  currentUser: any;
  userData: User;
  pastTrips: Trip[];
  failedRegister = false;
  errorMessage: string;
  isSubmit = false;

  constructor(
    private token: TokenStorageService, 
    private userService: UserService, 
    private tripService: TripService,
    private fb: FormBuilder) { }

    editPersonalDataForm = this.fb.group({
      firstName: [''],
      lastName: [''],
      dayOfBirth: ['']
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
    this.isSubmit = true;
    console.log(this.isSubmit);
    console.log(this.editPersonalDataForm.value);
    this.userService.editData(this.editPersonalDataForm.value).subscribe(
        response => console.log('Success!', response),
        err => {
            this.errorMessage = err.error.message;
        }
    );

    this.isSubmit = true;
}

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();

      this.userService.getData().subscribe(
        (response: User) => {
          this.userData = response;
        }
      )

      this.tripService.getPast().subscribe(
        (response: Trip[]) => {
          this.pastTrips = response;
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

  openEditPanel(){
    if(this.isEditPanelClicked == false)
    {
      this.isEditPanelClicked = true;
    }else if(this.isEditPanelClicked == true)
    {
      this.isEditPanelClicked = false;
    }
  }
}
