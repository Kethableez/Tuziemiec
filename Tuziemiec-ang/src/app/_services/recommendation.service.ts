import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Trip } from '../_model/trip';


@Injectable({
    providedIn: 'root'
  })
  export class RecommendationService{
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) { }

    public getRecommendation(): Observable<Trip[]> {
        return this.http.get<Trip[]>(this.apiServerUrl + '/recomendations/');
    }
  }