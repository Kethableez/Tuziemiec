import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment';
import { Review } from '../../_model/review';
import { Trip } from '../../_model/trip';
import { User } from '../../_model/user';
import { ParticipationService } from '../../_services/participation.service';
import { ReviewService } from '../../_services/review.service';
import { TokenStorageService } from '../../_services/token-storage.service';
import { TripService } from '../../_services/trip.service';
import { UserService } from '../../_services/user.service';

@Component({
  selector: 'app-trip-detail',
  templateUrl: './trip-detail.component.html',
  styleUrls: ['./trip-detail.component.css']
})
export class TripDetailComponent implements OnInit {
  isLoggedIn = false;
  currentUser: any;
  currUser: User;
  trip: Trip;
  reviews: Review[];
  isEnrolled: boolean;
  id: number;
  images: String[] = [];
  tripImages: string[] = [];

  viewPhoto = false;
  selectedPhoto: string = ""

  filenames: string[];

  message: string;
  goodResponse = false;
  badResponse = false;
  showMessage = true;

  constructor(
    private token: TokenStorageService,
    private tripService: TripService,
    private reviewService: ReviewService,
    private participationService: ParticipationService,
    private route: ActivatedRoute,
    private userService: UserService
    ) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
      
      this.userService.getData().subscribe(
        (response: User) => {
          this.currUser = response;
        }
      )
    }

    if(this.isLoggedIn) {
      this.id = +this.route.snapshot.paramMap.get('id');

      this.participationService.isUserEnrolled(this.id).subscribe(
        (response: boolean) => {
          this.isEnrolled = response;
        }
      )

      this.tripService.getTrip(this.id).subscribe(
        (response: Trip) => {
          this.trip = response;

          this.reviewService.getReviews(response.template.id).subscribe(
            (response: Review[]) => {
              this.reviews = response;
            }
          )

          this.tripService.getFilenames(this.trip.template.name).subscribe(
            (response: string[]) => {
              this.filenames = response;
              for (let i = 0; i < this.filenames.length; i++){
                this.tripImages.push("http://localhost:8080/images/getTripPhoto?fileName=" + this.filenames[i] + "&tripName=" + this.trip.template.name);
              }

              for (let i = 0; i < this.trip.template.attractions.length; i++) {
                this.tripImages.push("http://localhost:8080/images/getAttractionPhoto?attractionId=" + this.trip.template.attractions[i].id);
              }
              this.selectedPhoto = this.tripImages[0];
            }
          )
        }
      )
    }
  }

  like(reviewId: number) {
    this.reviewService.likeReview(reviewId).subscribe(
      response => {
        console.log('Success!', response);
        this.reviews.find(r => r.id == reviewId).upVote += 1;
    },
      err => {
          this.message = err.error.message;

      }
    )
  }

  getPhoto(userId: number): string {
    return "http://localhost:8080/images/getAvatar/" + userId;
  }


  dislike(reviewId: number) {
    this.reviewService.dislikeReview(reviewId).subscribe(
      response =>{
        console.log('Success!', response);
        this.reviews.find(r => r.id == reviewId).downVote -= 1;
      }, 
      err => {
          this.message = err.error.message;
      }
    )
  }

  addCurrentUserToTrip(): void {
    this.participationService.postParticipation(this.id).subscribe(
      response => {
        this.onResponse(response.message, 1);
        this.isEnrolled = true;
        this.trip.booking += 1;
      },
      err => {
        this.onResponse(err.error.message, 2);
      }
    )
  }

  removeCurrentUserFromTrip(): void {
    this.participationService.unparticipeTrip(this.id).subscribe(
      response => {
        this.onResponse("Wypisano", 1);
        this.isEnrolled = false;
        this.trip.booking -= 1;
      },
      err => {
        this.onResponse(err.error.message, 2);
      }
      )
    
  }

  onPhotoClick(photoName: string) {
    this.viewPhoto = true;
    this.selectedPhoto = photoName;
  }

  showGallery() {
    this.viewPhoto = true;
  }

  onClose() {
    this.viewPhoto = false;
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

