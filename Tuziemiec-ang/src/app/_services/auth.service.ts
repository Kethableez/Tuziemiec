import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  login(credentials): Observable<any> {
    return this.http.post(this.apiServerUrl + '/api/auth/' + 'login', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }
}
