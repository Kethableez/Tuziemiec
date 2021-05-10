import { MapsAPILoader } from '@agm/core';
import { Component, ElementRef, NgZone, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AttractionService } from '../../../_services/attraction.service';
import { TokenStorageService } from '../../../_services/token-storage.service';


@Component({
  selector: 'app-attraction-creator',
  templateUrl: './attraction-creator.component.html',
  styleUrls: ['./attraction-creator.component.css']
})
export class AttractionCreatorComponent implements OnInit {
  isLoggedIn = false;
  isSubmit = false;
  isFailed = false;
  message = "dodano pomyślnie";

  lat: number;
  lon: number;
  zoom: number;

  @ViewChild('search')
  public searchElementRef: ElementRef;

  private geoCoder;

  get name() {
    return this.attractionForm.get('name');
  }

  get place() {
    return this.attractionForm.get('place');
  }

  get description() {
    return this.attractionForm.get('description');
  }

  attractionForm = this.fb.group({
    name: ['', Validators.required],
    place: ['', Validators.required],
    description: ['', Validators.required],
    latitude: ['', Validators.required],
    longitude: ['', Validators.required]
  });

  constructor(
    private token: TokenStorageService,
    private fb: FormBuilder,
    private attractionService: AttractionService,
    private mapsAPILoader: MapsAPILoader,
    private ngZone: NgZone) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
    }

    this.mapsAPILoader.load().then(() => {

      this.setCurrentLocation();
      this.geoCoder = new google.maps.Geocoder;

      let autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement);
      autocomplete.addListener("place_changed", () => {
        this.ngZone.run(() => {
          let place: google.maps.places.PlaceResult = autocomplete.getPlace();

          if (place.geometry === undefined || place.geometry === null) {
            return;
          }

          this.lat = place.geometry.location.lat();
          this.lon = place.geometry.location.lng();
          this.zoom = 12;
        });
      });
    });

  }

  private setCurrentLocation() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.lat = position.coords.latitude;
        this.lon = position.coords.longitude;
        this.zoom = 15;
      })
    }
  }

  getPosition($event: google.maps.MouseEvent) {
    console.log($event);
    this.lat = $event.latLng.lat();
    this.lon = $event.latLng.lng();

    this.attractionForm.patchValue({
      latitude: this.lat,
      longitude: this.lon
    })
  }

  onSubmit() {
    this.attractionService.createAttraction(this.attractionForm.value).subscribe(
      response => {
        console.log(response)
        this.message = response.message
      },
      err => {
        console.log(err.error.message)
        this.message = err.error.message
        this.isFailed = true
      }
    );
    this.isSubmit = true;
  }

  reloadPage(): void {
    window.location.reload();
  }

}
