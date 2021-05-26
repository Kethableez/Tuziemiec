import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Trip } from '../../_model/trip';
import { ParticipationService } from '../../_services/participation.service';
import { TokenStorageService } from '../../_services/token-storage.service';
import { TripService } from '../../_services/trip.service';

@Component({
  selector: 'app-incoming-trips',
  templateUrl: './incoming-trips.component.html',
  styleUrls: ['./incoming-trips.component.css']
})
export class IncomingTripsComponent implements OnInit {

  isLoggedIn = false;
  currentUser: any;
  incomingTrips: Trip[];
  message: string;
  goodResponse = false;
  badResponse = false;
  showMessage = true;

  constructor(
    private token: TokenStorageService,
    private tripService: TripService,
    private participationService: ParticipationService) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }

    if (this.isLoggedIn) {
      this.tripService.getIncomingTrips().subscribe(
        (response: Trip[]) => {
          this.incomingTrips = response;
        }
      )
    }
  }

  reloadPage(): void {
    window.location.reload();
  }

  removeCurrentUserFromTrip(TripId): void {
    this.participationService.unparticipeTrip(TripId).subscribe(
      response => {
        this.onResponse("Wypisano z wycieczki!", 1);
        this.RemoveFromTrip(TripId);
      },

      err => {
        this.onResponse(err.error.message, 2);
      }
    );
    // Wywalic reloada

  }

  onClick(){
    this.showMessage = false;
    this.message = "";
    this.goodResponse = false;
    this.badResponse = false;
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

  RemoveFromTrip(TripId: number) {
    this.incomingTrips.forEach((Trip, index) => {
      if(Trip.id == TripId) this.incomingTrips.splice(index, 1);
    })
  }
}
