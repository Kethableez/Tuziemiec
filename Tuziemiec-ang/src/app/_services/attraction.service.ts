import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Attraction } from '../_model/attraction';


@Injectable({
    providedIn: 'root'
  })

  export class AttractionService {
    private apiServerUrl = environment.apiBaseUrl;
    private attractionUrl = '/attractions';
  
    constructor(private http: HttpClient) { }
  
    public getAll(): Observable<Attraction[]> {
        return this.http.get<Attraction[]>(this.apiServerUrl + this.attractionUrl + '/all');
    }

    public getById(id: number): Observable<Attraction>{
      return this.http.get<Attraction>(this.apiServerUrl + this.attractionUrl + '/' + id);
    }

    public getAllByPlace(place: string): Observable<Attraction[]> {
      return this.http.get<Attraction[]>(this.apiServerUrl + this.attractionUrl + '/all/' + place);
    }
    public createAttraction(attractionData){
      return this.http.post<any>(this.apiServerUrl + this.attractionUrl + '/create_attraction', attractionData);
    }


    // public getPast(): Observable<Trip[]> {
    //   return this.http.get<Trip[]>(this.apiServerUrl + '/trip/guide_history');
    // }
  }