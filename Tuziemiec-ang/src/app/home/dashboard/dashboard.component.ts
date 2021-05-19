import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/_model/user';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  currentUser: any;
  userData: User;
  isLoggedIn = false;

  constructor(    
    private token: TokenStorageService, 
    private userService: UserService, ) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }
    if (this.isLoggedIn == true){
      this.userService.getData().subscribe(
        (response: User) => {
          this.userData = response;
        }
      )    
    }
  }
}
