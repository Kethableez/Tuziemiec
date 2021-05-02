import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Trip } from '../_model/trip';
import { TripTemplate } from '../_model/tripTemplate';

@Injectable({
    providedIn: 'root'
  })
  export class TripService {
    private apiServerUrl = environment.apiBaseUrl;
  
    constructor(private http: HttpClient) { }
  
    public createTrip(tripData){
      return this.http.post<any>(this.apiServerUrl + '/trip/create_trip', tripData);
    }

    public createTemplate(templateData){
      return this.http.post<any>(this.apiServerUrl + '/trip/create_template', templateData);
    }

    public getTemplates(): Observable<TripTemplate[]> {
      return this.http.get<TripTemplate[]>(this.apiServerUrl + '/trip/templates');
    }

    public getTrip(id: number): Observable<any> {
      return this.http.get<Trip>(this.apiServerUrl + '/trip/' + id);
    }

    public getAvailableTrips(): Observable<Trip[]> {
      return this.http.get<Trip[]>(this.apiServerUrl + '/trip/available');
    }

    public getPastTrips(): Observable<Trip[]> {
      return this.http.get<Trip[]>(this.apiServerUrl + '/participation/user_past');
    }

    public getIncomingTrips(): Observable<Trip[]> {
      return this.http.get<Trip[]>(this.apiServerUrl + '/participation/user_incoming');
    }

    public getOrganisedTrips(): Observable<Trip[]> {
      return this.http.get<Trip[]>(this.apiServerUrl + '/trip/created_trips');
    }
  }