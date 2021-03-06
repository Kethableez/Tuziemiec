import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { User } from '../_model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiServerUrl = environment.apiBaseUrl;


  constructor(private http: HttpClient) { }

  register(userData) {
    return this.http.post<any>(this.apiServerUrl + '/api/auth/register', userData);
  }

  login(userData) {
    return this.http.post<any>(this.apiServerUrl + '/api/auth/login', userData);
  }

  getData(): Observable<any> {
    return this.http.get<User>(this.apiServerUrl + '/user/data');
  }

  editData(userData){
    return this.http.put<any>(this.apiServerUrl + '/user/edit', userData);
  }

  getPhoto() {
    return this.http.get<any>(this.apiServerUrl + '/images/getAvatar')
  }

  uploadPhoto(photo: FormData) {
    return this.http.post<any>(this.apiServerUrl + '/images/uploadAvatar', photo);
  }
}
