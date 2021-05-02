import { Component, OnInit } from '@angular/core';
import { Trip } from '../_model/trip';
import { TokenStorageService } from '../_services/token-storage.service';
import { TripService } from '../_services/trip.service';

@Component({
  selector: 'app-incoming-trips',
  templateUrl: './incoming-trips.component.html',
  styleUrls: ['./incoming-trips.component.css']
})
export class IncomingTripsComponent implements OnInit {

  isLoggedIn = false;
  currentUser: any;
  incomingTrips: Trip[];

  constructor(
    private token: TokenStorageService,
    private tripService: TripService) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }

    if(this.isLoggedIn) {
      this.tripService.getIncomingTrips().subscribe(
        (response: Trip[]) => {
          this.incomingTrips = response;
        }
      )
    }
  }

}
