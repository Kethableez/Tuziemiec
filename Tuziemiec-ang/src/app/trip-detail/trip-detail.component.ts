import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Review } from '../_model/review';
import { Trip } from '../_model/trip';
import { ReviewService } from '../_services/review.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { TripService } from '../_services/trip.service';

@Component({
  selector: 'app-trip-detail',
  templateUrl: './trip-detail.component.html',
  styleUrls: ['./trip-detail.component.css']
})
export class TripDetailComponent implements OnInit {
  isLoggedIn = false;
  currentUser: any;
  trip: Trip;
  reviews: Review[];
  id: number;

  constructor(
    private token: TokenStorageService,
    private tripService: TripService,
    private reviewService: ReviewService,
    private route: ActivatedRoute
    ) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }

    if(this.isLoggedIn) {
      this.id = +this.route.snapshot.paramMap.get('id');

      this.tripService.getTrip(this.id).subscribe(
        (response: Trip) => {
          this.trip = response;

          this.reviewService.getReviews(response.template.id).subscribe(
            (response: Review[]) => {
              this.reviews = response;
            }
          )
        }
      )
    }


  }

}
