import { Component, OnInit } from '@angular/core';
import { Participation } from 'src/app/_model/participation';
import { Trip } from 'src/app/_model/trip';
import { User } from 'src/app/_model/user';
import { ParticipationService } from 'src/app/_services/participation.service';
import { RecommendationService } from 'src/app/_services/recommendation.service';
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
  pastList: Participation[];
  incomingList: Participation[];
  recommendations: Trip[];
  unreviewedCount = 0;
  incomingCount = 0;

  constructor(    
    private token: TokenStorageService, 
    private userService: UserService, 
    private participationService: ParticipationService,
    private recommendationService: RecommendationService) { }

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

    if (this.isLoggedIn == true){
      this.participationService.userPast().subscribe(
        (response: Participation[]) =>{
          this.pastList = response;

          this.pastList.forEach(participation => {
            if (participation.isReviewed == false) this.unreviewedCount++;
          });
        }
      )
    }

    if (this.isLoggedIn == true){
      this.participationService.userIncoming().subscribe(
        (response: Participation[]) =>{
          this.incomingList = response;

          this.incomingList.forEach(participation => {
            if (participation.isReviewed == false) this.incomingCount++;
          });
        }
      )
    }

    if (this.isLoggedIn == true) {
      this.recommendationService.getRecommendation().subscribe(
        (response: Trip[]) => {
          this.recommendations = response;
          console.log("Kurwa");
        }
      )
    }

  }
}
