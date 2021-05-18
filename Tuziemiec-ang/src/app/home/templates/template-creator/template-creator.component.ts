import { MapsAPILoader } from '@agm/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Attraction } from '../../../_model/attraction'
import { AttractionService } from '../../../_services/attraction.service';
import { TokenStorageService } from '../../../_services/token-storage.service';
import { TripService } from '../../../_services/trip.service';


@Component({
  selector: 'app-template-creator',
  templateUrl: './template-creator.component.html',
  styleUrls: ['./template-creator.component.css']
})
export class TemplateCreatorComponent implements OnInit {

  step_1 = true;
  step_2 = false;

  selectedAttractions: Attraction[] = [];

  isLoggedIn = false;
  attractionList: Attraction[];

  isSubmit = false;
  isFailed = false;
  message: string;

  idList: number[] = [];

  get place() {
    return this.search.get('place');
  }

  search = this.fb.group({
    place: ['', Validators.required]
  })

  get template_place() {
    return this.templateForm.get('place');
  }

  get name() {
    return this.templateForm.get('name');
  }

  get description() {
    return this.templateForm.get('description')
  }

  templateForm = this.fb.group({
    name: ['', Validators.required],
    place: ['', Validators.required],
    description: ['', Validators.required],
    attraction_id: [this.idList]
  })

  constructor(
    private token: TokenStorageService,
    private attractionService: AttractionService,
    private tripService: TripService,
    private fb: FormBuilder,
    private mapsApiLoader: MapsAPILoader) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
    }
  }

  findAttractions(place: string) {
    this.attractionService.getAllByPlace(place).subscribe(
      (response: Attraction[]) => {
        this.attractionList = response;
      }
    )

    this.search.reset;
  }

  addToSelected(att: Attraction){
    if(!this.selectedAttractions.includes(att)){
      this.selectedAttractions.push(att);
      this.idList.push(att.id);
    }
  }

  addToIdList(id: number){
    if(!this.idList.includes(id)){
      this.idList.push(id);
    }
  }

  onClick(){
    this.step_1 = false;
    this.step_2 = true;

    this.selectedAttractions.forEach(item => {
      this.addToIdList(item.id);
    });
  }

  // onAttractionAdd(m: marker) {
  //   this.markerList.push(m);
  // }

  onAttractionAdd(att: Attraction) {
    this.selectedAttractions.push(att);
    this.idList.push(att.id);
  }

  onAttractionRemove(att: Attraction){
    this.selectedAttractions.forEach((value, index) =>{
      if(value.name == att.name) this.selectedAttractions.splice(index, 1);
    })

    this.idList.forEach((value, index) =>{
      if(value == att.id) this.idList.splice(index, 1);
    })
  };

  onSubmit(){
    this.tripService.createTemplate(this.templateForm.value).subscribe(
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
