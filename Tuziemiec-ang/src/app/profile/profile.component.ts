import { Component, OnInit } from '@angular/core';
import { User } from '../_model/user';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  isLoggedIn = false;
  currentUser: any;
  userData: User;

  constructor(private token: TokenStorageService, private userService: UserService) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();

    //   this.data = this.userService.getData().subscribe(
    //     data => {
    //       this.data = data;
    //     },
    //   );
    // }
      this.userService.getData().subscribe(
        (response: User) => {
          this.userData = response;
        }
      )

    }
  }

  logout(): void {
    this.token.signOut();
    window.location.reload();
  }
}
