import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Participation } from '../_model/participation';


@Injectable({
    providedIn: 'root'
  })
  export class ParticipationService{
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) { }

    public postParticipation(TripId: number): Observable <any>{
        return this.http.post<number>(this.apiServerUrl + '/participation/participe/' + TripId, {});
      }

    public unparticipeTrip(TripId: number): Observable <void> {
      return this.http.delete<void>(this.apiServerUrl + '/participation/unparticipe/' + TripId);
    }

    public userPast(): Observable <Participation[]> {
      return this.http.get<Participation[]>(this.apiServerUrl + '/participation/user_past_part');
    }
  }