import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css']
})
export class MapsComponent implements OnInit {

  latitude = 52.065162;
  longitude = 19.252522;
  lat?: number;
  lng?: number;
  isLocationChosen = false;

  // constructor() { }

  ngOnInit(): void {
    
  }

  onChoseLocation(event){
    console.log(event);
    this.lat =  52.065162; //event.coords.lat;
    this.lng = 19.252522; //event.coords.lng;
    this.isLocationChosen = true;
  }
  

}
