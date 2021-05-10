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
  resp: Trip;
  message: string;
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
      }
    )
  }

  reloadPage(): void {
    window.location.reload();
  }

  isEnrolled(id: number): boolean {
     if(this.partList.find(p => p.tripId == id) != null) return true;
     else return false;
  }

  addCurrentUserToTrip(TripId: number): void {
    this.participationService.postParticipation(TripId).subscribe(
      response => {
        console.log(response)
        this.message = response.message
      },

      err => {
        console.log(err.error.message)
        this.message = err.error.message
      }
    )
    // this.reloadPage();
  }

}
