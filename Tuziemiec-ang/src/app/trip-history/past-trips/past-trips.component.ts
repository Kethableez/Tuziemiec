import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Participation } from '../../_model/participation';
import { Trip } from '../../_model/trip';
import { ParticipationService } from '../../_services/participation.service';
import { ReviewService } from '../../_services/review.service';
import { TokenStorageService } from '../../_services/token-storage.service';
import { TripService } from '../../_services/trip.service';

@Component({
  selector: 'app-past-trips',
  templateUrl: './past-trips.component.html',
  styleUrls: ['./past-trips.component.css']
})
export class PastTripsComponent implements OnInit {

  get commentHeader() {
    return this.commentForm.get('commentHeader');
  }

  get commentBody() {
    return this.commentForm.get('commentBody');
  }

  get rating() {
    return this.commentForm.get('rating');
  }

  isLoggedIn = false;
  currentUser: any;
  pastTrips: Trip[];
  pastParticipation: Participation[];
  tripSelector: Trip;
  enabled = false;
  b_rating = 0;
  errorMessage: string;
  style = 'not-blurred'

  message: string;
  goodResponse = false;
  badResponse = false;
  showMessage = true;

  commentForm = this.fb.group({
    tripId: ['', Validators.required],
    commentHeader: ['', Validators.required],
    commentBody: ['', Validators.required],
    rating: ['', Validators.required],
  })

  constructor(
    private token: TokenStorageService,
    private tripService: TripService,
    private participationService: ParticipationService,
    private reviewService: ReviewService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }

    if (this.isLoggedIn) {
      this.tripService.getPastTrips().subscribe(
        (response: Trip[]) => {
          this.pastTrips = response;
        }
      )

      this.participationService.userPast().subscribe(
        (response: Participation[]) => {
          this.pastParticipation = response;
        }
      )
    }
  }

  onSubmit(tripId: number) {
    this.reviewService.addReview(this.commentForm.value, tripId).subscribe(
      response => {
        this.onResponse(response.message, 1);
        this.setReviewed(tripId);
        this.closeCommentForm();

      },
      err => {
        this.onResponse(err.error.message, 2);
      }
    );
    // Pousuwac reloady, zamiienić to na podmianę ikonki!

  }

  openCommentForm(t: Trip) {
    this.enabled = true;
    this.tripSelector = t;

    this.commentForm.patchValue({
      tripId: this.tripSelector.id
    })

    this.style = 'blurred';
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

  setReviewed(id: number) {
    this.pastParticipation.find(p => p.tripId == id).isReviewed = true;
  }

  isTripReviewed(id: number): boolean {
    return this.pastParticipation.find(p => p.tripId == id).isReviewed;
  }

  closeCommentForm() {
    this.enabled = false;
    this.style = 'not-blurred';
  }

  reloadPage(): void {
    window.location.reload();
  }

}
