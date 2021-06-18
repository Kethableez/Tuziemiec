import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UsersAttraction } from 'src/app/_model/usersAttraction';
import { Attraction } from '../../_model/attraction';
import { AttractionService } from '../../_services/attraction.service';
import { TokenStorageService } from '../../_services/token-storage.service';

@Component({
  selector: 'app-attraction',
  templateUrl: './attraction.component.html',
  styleUrls: ['./attraction.component.css']
})
export class AttractionComponent implements OnInit {

  attractions: Attraction[];
  isLoggedIn = false;
  enabled = false;
  attractionSelector: Attraction;
  seenAttractions: UsersAttraction[];
  tempRating = 0;

  searchText: string;

  message: string;
  goodResponse = false;
  badResponse = false;
  showMessage = true;

  ratingForm = this.fb.group({
    attractionId: ['', Validators.required],
    rating: ['', Validators.required]
  })

  constructor(
    private token: TokenStorageService,
    private attractionService: AttractionService,
    private fb: FormBuilder) { }

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

      this.attractionService.getUserSeenAttractions().subscribe(
        (response: UsersAttraction[]) => {
          this.seenAttractions = response;
        }
      )
    }
  }

  infoClick(att: Attraction) {
    this.enabled = true;
    this.attractionSelector = att;

    if (this.isRated(this.attractionSelector.id)) {
      this.ratingForm.patchValue({
        attractionId: this.attractionSelector.id
      })
    }
  }

  exitClick() {
    this.enabled = false;
    this.ratingForm.reset();
  }

  onSubmit() {
    if (this.attractions.find(obj => obj.id === this.attractionSelector.id).rating == 0){
      this.attractions.find(obj => obj.id === this.attractionSelector.id).rating = this.ratingForm.get('rating').value
    }
    else {
      this.attractions.find(obj => obj.id === this.attractionSelector.id).rating = (this.attractions.find(obj => obj.id === this.attractionSelector.id).rating + this.ratingForm.get('rating').value) / 2;
    }

    this.attractionService.rateAttraction(this.ratingForm.value).subscribe(
      response => {
        this.onResponse(response.message, 1);
      },
      err => {
        this.onResponse(err.error.message, 2);
      }
    )
  }

  isRated(attractionId: number): boolean {
    var isRated = true;
    for (var i = 0; i < this.seenAttractions.length; i++) {
      if (this.seenAttractions[i].attractionId == attractionId) {
        if (!this.seenAttractions[i].isReviewed) {
          isRated = false;
          break;
        }
      }
    }
    return !isRated;
  }

  onClick(){
    this.showMessage = false;
    this.message = "";
    this.goodResponse = false;
    this.badResponse = false;
  }

  showPhoto(attId: number): string {
    return "http://localhost:8080/images/getAttractionPhoto?attractionId=" + attId;
  }

  onResponse(responseMessage: string, responseSelector: number) {
    if (responseSelector == 1) {
      this.goodResponse = true;
      this.badResponse = false;
    }
    else if (responseSelector == 2) {
      this.goodResponse = false;
      this.badResponse = true;
    }

    this.showMessage = true;
    this.message = responseMessage;

    this.ratingForm.reset;
  }

  reloadPage(): void {
    window.location.reload();
  }
}
