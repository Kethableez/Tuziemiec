import { Component, OnInit } from '@angular/core';
import { Trip } from '../../_model/trip';
import { TokenStorageService } from '../../_services/token-storage.service';
import { TripService } from '../../_services/trip.service';
import { ParticipationService } from '../../_services/participation.service';
import { Participation } from '../../_model/participation';

@Component({
  selector: 'app-trips',
  templateUrl: './trips.component.html',
  styleUrls: ['./trips.component.css']
})
export class TripsComponent implements OnInit {

  currentUser: any;
  avTrips: Trip[];
  partList: Participation[];
  partId: number[] = [];
  resp: Trip;
  message = "";
  goodResponse = false;
  badResponse = false;
  showMessage = true;
  searchText;

  constructor(private token: TokenStorageService,
    private tripService: TripService,
    private participationService: ParticipationService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();

    this.tripService.getAvailableTrips().subscribe(
      (response: Trip[]) => {
        this.avTrips = response;
      }
    )

    this.participationService.userIncoming().subscribe(
      (response: Participation[]) => {
        this.partList = response;
        this.partList.forEach(element => {
          this.partId.push(element.tripId);
        });
      }
    )
  }

  reloadPage(): void {
    window.location.reload();
  }

  isEnrolled(id: number): boolean {
    //  if(this.partList.find(p => p.tripId == id) != null) return true;
    if(this.partId.find(p => p == id) != null) return true;
     else return false;
  }

  addCurrentUserToTrip(TripId: number): void {
    this.participationService.postParticipation(TripId).subscribe(
      response => {
        this.onResponse(response.message, 1);
        this.partId.push(TripId);
      },

      err => {
        this.onResponse(err.error.message, 2);
      }
    )
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
}
