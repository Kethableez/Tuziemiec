import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  url = 'http://localhost:8080/api/auth/register';

  constructor(private http: HttpClient) { }

  register(userData) {
    return this.http.post<any>(this.url, userData);
  }
}