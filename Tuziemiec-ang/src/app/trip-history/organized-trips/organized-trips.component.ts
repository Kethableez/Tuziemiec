import { Component, OnInit } from '@angular/core';
import { Trip } from '../../_model/trip';
import { TokenStorageService } from '../../_services/token-storage.service';
import { TripService } from '../../_services/trip.service';
import { FormBuilder, Validators } from '@angular/forms';
import { CustomeDateValidators } from '../../home/trips/trip-creator/from-to-date.validator'

@Component({
  selector: 'app-organized-trips',
  templateUrl: './organized-trips.component.html',
  styleUrls: ['./organized-trips.component.css']
})
export class OrganizedTripsComponent implements OnInit {

  get startDate() {
    return this.editTripForm.get('startDate');
  }

  get endDate() {
    return this.editTripForm.get('endDate');
  }

  get userLimit() {
    return this.editTripForm.get('userLimit');
  }

  isLoggedIn = false;
  currentUser: any;
  organizedTrips: Trip[];
  tripSelector: Trip;
  enabled = false;
  errorMessage: string;
  style = 'not-blurred'

  message: string;
  goodResponse = false;
  badResponse = false;
  showMessage = true;

  editTripForm = this.fb.group({
    startDate: ['', Validators.required],
    endDate: ['', Validators.required],
    userLimit: ['', [Validators.required, Validators.min(1)]],
  }, {
    validator: CustomeDateValidators.fromToDate('startDate', 'endDate')
  })

  constructor(
    private token: TokenStorageService,
    private tripService: TripService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }

    if (this.isLoggedIn) {
      this.tripService.getOrganisedTrips().subscribe(
        (response: Trip[]) => {
          this.organizedTrips = response;
        }
      )
    }
  }

  openEditForm(t: Trip) {
    this.enabled = true;
    this.tripSelector = t;

    this.editTripForm.patchValue({
      tripId: this.tripSelector.id
    })
    this.style = 'blurred';
  }

  closeCommentForm() {
    this.enabled = false;
    this.style = 'not-blurred';
  }

  onSubmit(tripId: number) {
    console.log(this.editTripForm.value);
    this.tripService.editTrip(this.editTripForm.value, tripId).subscribe(
      response => { 
        this.onResponse(response.message, 1);
      },
      err => {
        this.onResponse(err.error.message, 2);
      }
    );
  }

  onClick() {
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



  reloadPage(): void {
    window.location.reload();
  }

}
