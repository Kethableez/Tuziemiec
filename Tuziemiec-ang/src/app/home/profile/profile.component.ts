import { Component, OnInit, SecurityContext } from '@angular/core';
import { Trip } from '../../_model/trip';
import { User } from '../../_model/user';
import { TokenStorageService } from '../../_services/token-storage.service';
import { TripService } from '../../_services/trip.service';
import { UserService } from '../../_services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { DomSanitizer, SafeStyle, SafeUrl } from '@angular/platform-browser';

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
    }

    if (this.isLoggedIn == true) {
      this.userService.getData().subscribe(
        (response: User) => {
          this.userData = response;
          this.image = "http://localhost:8080/images/getAvatar/" + this.userData.username;
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
        console.log(res);
      },
      (err) => {
        console.log(err)
      }
    )
  }

  reloadPage(): void {
    window.location.reload();
  }

}
