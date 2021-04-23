import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators  } from '@angular/forms';
import { TokenStorageService } from '../_services/token-storage.service';
import { TripService } from '../_services/trip.service';

@Component({
  selector: 'app-trip-creator',
  templateUrl: './trip-creator.component.html',
  styleUrls: ['./trip-creator.component.css']
})
export class TripCreatorComponent implements OnInit {

  constructor(private fb: FormBuilder, private token: TokenStorageService, private tripService: TripService ) { }

  isLoggedIn = false;
  isSubmit = false;
  currentUser: any;

  get city() {
    return this.createTrip.get('city');
  }

  get name() {
    return this.createTrip.get('name');
  }

  get description() {
    return this.createTrip.get('description');
  }

  get user_limit() {
    return this.createTrip.get('limit');
  }

  get trip_date() {
    return this.createTrip.get('tripDate');
  }

  createTrip = this.fb.group({
    city: ['', Validators.required],
    name: ['', Validators.required],
    description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(250)]],
    limit: ['', [Validators.required, Validators.min(1)]],
    tripDate: ['', Validators.required],
  });

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }
  }

  onSubmit(){
    
    this.tripService.createTrip(this.createTrip.value).subscribe(
      response => console.log('Success!', response),
      error => console.error('Error!', error)
    );
    this.isSubmit=true;
  }
}
