import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';


@Injectable({
    providedIn: 'root'
  })
  export class ParticipationService{
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) { }

    public postParticipation(TripId: number): Observable <any>{
        return this.http.post<number>(this.apiServerUrl + '/participation/participe/' + TripId, {});
      }
  }