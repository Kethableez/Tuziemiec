import { MapsAPILoader } from '@agm/core';
import { Component, ElementRef, NgZone, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Attraction } from 'src/app/_model/attraction';
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
  enabled = false;
  attractionSelector: Attraction;

  message: string;
  goodResponse = false;
  badResponse = false;
  showMessage = true;

  selectedFile = null;

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
    coverPhoto: ['', Validators.required],
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

  onSelectFile(event){
    if (event.target.files.length > 0) {
      const f = event.target.files[0];
      this.selectedFile = event.target.files[0];
      this.attractionForm.patchValue({
        'coverPhoto': event.target.files[0].name
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
        this.onResponse(response.message, 1);
        var formData = new FormData();
        formData.append('imageFile', this.selectedFile);
        this.attractionService.uploadPhoto(formData).subscribe(
          (res) => console.log(res),
          (err) => console.log(err)
        )
      },
      err => {
        this.onResponse(err.error.message, 2);
      }
    );
  }

  onClick(){
    this.showMessage = false;
    this.message = "";
    this.goodResponse = false;
    this.badResponse = false;
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

    this.attractionForm.reset();
  }

  reloadPage(): void {
    window.location.reload();
  }

}
