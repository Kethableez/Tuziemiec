import { Component, OnInit } from '@angular/core';
import { Trip } from '../_model/trip';
import { TokenStorageService } from '../_services/token-storage.service';
import { TripService } from '../_services/trip.service';

@Component({
  selector: 'app-past-trips',
  templateUrl: './past-trips.component.html',
  styleUrls: ['./past-trips.component.css']
})
export class PastTripsComponent implements OnInit {

  isLoggedIn = false;
  currentUser: any;
  pastTrips: Trip[];

  constructor(
    private token: TokenStorageService,
    private tripService: TripService
  ) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }

    if(this.isLoggedIn) {
      this.tripService.getPastTrips().subscribe(
        (response: Trip[]) => {
          this.pastTrips = response;
        }
      )
    }
  }

}
