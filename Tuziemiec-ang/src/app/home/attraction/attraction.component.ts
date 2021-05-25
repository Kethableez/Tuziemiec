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
  errorMessage: string;

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
    this.attractionService.rateAttraction(this.ratingForm.value).subscribe(
      response => console.log('Success!', response),
      err => {
        this.errorMessage = err.error.message;
        // this.reloadPage();
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

  reloadPage(): void {
    window.location.reload();
  }
}
