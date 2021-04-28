import { Component, OnInit } from '@angular/core';
import { Trip } from '../_model/trip';
import { TokenStorageService } from '../_services/token-storage.service';
import { TripService } from '../_services/trip.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  isLoggedIn = false;
  currentUser: any;
  avTrips: Trip[];
  resp: Trip;


  constructor(private token: TokenStorageService, private tripService: TripService) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();

      this.tripService.getAvailableTrips().subscribe(
        (response: Trip[]) => {
          this.avTrips = response;
          this.resp = this.avTrips[1];
        }
      )
    }

  }

  logout(): void {
    this.token.signOut();
  }

}
