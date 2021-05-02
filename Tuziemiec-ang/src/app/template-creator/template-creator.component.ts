import { i18nMetaToJSDoc } from '@angular/compiler/src/render3/view/i18n/meta';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Attraction } from '../_model/attraction'
import { AttractionService } from '../_services/attraction.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { TripService } from '../_services/trip.service';

@Component({
  selector: 'app-template-creator',
  templateUrl: './template-creator.component.html',
  styleUrls: ['./template-creator.component.css']
})
export class TemplateCreatorComponent implements OnInit {

  step_1 = true;
  step_2 = false;

  isLoggedIn = false;
  attractionList: Attraction[];

  isSubmit = false;
  isFailed = false;
  message: string;

  selectedAttractions: Attraction[] = [];
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
    private fb: FormBuilder) { }

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
    }
  }

  addToIdList(id: number){
    if(!this.idList.includes(id)){
      this.idList.push(id);
    }
  }
  
  removeFromSelected(id: number){
    this.selectedAttractions.forEach((value, index) => {
      if(value.id == id) this.selectedAttractions.splice(index, 1);
    })
  };

  onClick(){
    this.step_1 = false;
    this.step_2 = true;

    this.selectedAttractions.forEach(item => {
      this.addToIdList(item.id);
    });
  }

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
