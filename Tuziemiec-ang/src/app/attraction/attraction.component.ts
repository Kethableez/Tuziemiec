import { Component, OnInit } from '@angular/core';
import { Attraction } from '../_model/attraction';
import { AttractionService } from '../_services/attraction.service';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-attraction',
  templateUrl: './attraction.component.html',
  styleUrls: ['./attraction.component.css']
})
export class AttractionComponent implements OnInit {

  attractions: Attraction[];
  isLoggedIn = false;
  //currentUser: any;


  constructor(
    private token: TokenStorageService, 
    private attractionService: AttractionService) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      //this.currentUser = this.token.getUser();

      this.attractionService.getAll().subscribe(
        (response: Attraction[]) => {
          this.attractions = response;
          //this.respo = this.avTrips[1];
        }
      )
    }
  }

}
