import { Component, OnInit } from '@angular/core';
import { Trip } from '../_model/trip';
import { User } from '../_model/user';
import { TokenStorageService } from '../_services/token-storage.service';
import { TripService } from '../_services/trip.service';
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
  pastTrips: Trip[];

  constructor(
    private token: TokenStorageService, 
    private userService: UserService, 
    private tripService: TripService) { }

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
}
