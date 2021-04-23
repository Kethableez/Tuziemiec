import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Trip } from '../_model/trip';

const API_URL = 'http://localhost:8080/trip';

@Injectable({
    providedIn: 'root'
  })
  export class TripService {
    private apiServerUrl = environment.apiBaseUrl;
  
    constructor(private http: HttpClient) { }
  
    public createTrip(tripData){
      return this.http.post<any>(this.apiServerUrl + '/trip/create', tripData);
    }

    public getTrip(id: number): Observable<any> {
      return this.http.get<Trip>(this.apiServerUrl + '/trip/' + id);
    }

    public getAvailableTrips(): Observable<Trip[]> {
      return this.http.get<Trip[]>(this.apiServerUrl + '/trip/available');
    }

    public getPast(): Observable<Trip[]> {
      return this.http.get<Trip[]>(this.apiServerUrl + '/trip/guide_history');
    }
  }