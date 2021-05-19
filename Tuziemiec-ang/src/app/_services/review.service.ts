import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Review } from '../_model/review';


@Injectable({
    providedIn: 'root'
  })
  export class ReviewService {
    private apiServerUrl = environment.apiBaseUrl;
    private apiReviewUrl = "/review"

    constructor(private http: HttpClient) { }

    public getReviews(tempateId: number): Observable<Review[]> {
        return this.http.get<Review[]>(this.apiServerUrl + this.apiReviewUrl +'/template_reviews/' + tempateId);
      }

    public addReview(commentData, tripId: number){
      return this.http.post<any>(this.apiServerUrl + this.apiReviewUrl + '/add_review', commentData);
    }

    public likeReview(reviewId: number) {
      return this.http.put<any>(this.apiServerUrl + this.apiReviewUrl + '/like_review/' + reviewId, {});
    } 

    public dislikeReview(reviewId: number) {
      return this.http.put<any>(this.apiServerUrl + this.apiReviewUrl + '/dislike_review/' + reviewId, {});
    } 

  }