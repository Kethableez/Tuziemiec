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
        console.log(this.apiServerUrl + this.apiReviewUrl +'/template_reviews/' + tempateId);
        return this.http.get<Review[]>(this.apiServerUrl + this.apiReviewUrl +'/template_reviews/' + tempateId);
      }

    public addReview(commentData, tripId: number){
      console.log(this.apiServerUrl + this.apiReviewUrl + '/add_review', commentData)
      return this.http.post<any>(this.apiServerUrl + this.apiReviewUrl + '/add_review', commentData);
    }

  }