import { Component, OnInit } from '@angular/core';
import { Trip } from '../_model/trip';
import { TokenStorageService } from '../_services/token-storage.service';
import { TripService } from '../_services/trip.service';

@Component({
  selector: 'app-organized-trips',
  templateUrl: './organized-trips.component.html',
  styleUrls: ['./organized-trips.component.css']
})
export class OrganizedTripsComponent implements OnInit {

  isLoggedIn = false;
  currentUser: any;
  organizedTrips: Trip[];

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
      this.tripService.getOrganisedTrips().subscribe(
        (response: Trip[]) => {
          this.organizedTrips = response;
        }
      )
    }
  }

}
