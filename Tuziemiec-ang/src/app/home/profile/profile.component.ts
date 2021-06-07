import { Component, OnInit, SecurityContext } from '@angular/core';
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
  image: String;
  tempImage: String;
  selectedFile = null;
  response = false;

  message: string;
  goodResponse = false;
  badResponse = false;
  showMessage = true;

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

  get firstName() {
    return this.editPersonalDataForm.get('firstName');
  }

  get lastName() {
    return this.editPersonalDataForm.get('lastName');
  }

  onSubmit() {
    console.log(typeof (this.userData.dayOfBirth));
    console.log(this.editPersonalDataForm.value);
    this.chceckDate();
    if (this.isDatePast){
      this.userService.editData(this.editPersonalDataForm.value).subscribe(
        response => {
          this.onResponse(response.message, 1);
          this.userData.firstName = this.editPersonalDataForm.get('firstName').value;
          this.userData.lastName = this.editPersonalDataForm.get('lastName').value;
          this.userData.dayOfBirth = this.editPersonalDataForm.get('dayOfBirth').value;
        },
        err => {
          this.onResponse(err.error.message, 2);
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

    if (this.isLoggedIn == true) {
      this.userService.getData().subscribe(
        (response: User) => {
          this.userData = response;
          this.image = "http://localhost:8080/images/getAvatar/" + this.userData.id;
        }
      )
    }
  }

  logout(): void {
    this.token.signOut();
  }

  onSelectFile(e) {
    if (e.target.files.length > 0) {
      const f = e.target.files[0];
      this.selectedFile = e.target.files[0];

      var reader = new FileReader();
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = (event: any) => {
        this.tempImage = event.target.result;
      }
    }
  }

  onUpload() {
    var formData = new FormData();
    formData.append('imageFile', this.selectedFile);
    this.userService.uploadPhoto(formData).subscribe(
      (res) => { 
        this.image = this.tempImage;
        this.onResponse(res.message, 1);
      },
      (err) => {
        this.onResponse(err.error.message, 2);
      }
    )
  }

  reloadPage(): void {
    window.location.reload();
  }

  chceckDate() : void{
    this.isDatePast = isPast(new Date(this.dayOfBirth.value));
    this.isTried = true;
}

onResponse(responseMessage: string, responseSelector: number) {
  if (responseSelector == 1) {
    this.goodResponse = true;
    this.badResponse = false;
  }
  else if (responseSelector == 2) {
    this.goodResponse = false;
    this.badResponse = true;
  }

  this.showMessage = true;
  this.message = responseMessage;
}

onClick(){
  this.showMessage = false;
  this.message = "";
  this.goodResponse = false;
  this.badResponse = false;
}

}
