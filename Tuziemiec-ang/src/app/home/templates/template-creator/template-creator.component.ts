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

  selectedAttractions: Attraction[] = [];

  isLoggedIn = false;
  attractionList: Attraction[];

  isSubmit = false;
  isFailed = false;
  
  message: string;
  goodResponse = false;
  badResponse = false;
  showMessage = false;

  image: string;
  selectedImage: string;
  images: string[] = [];
  tripName: string;
  
  selectedFiles: FileList;

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
    coverPhoto: [''],
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
    this.selectedAttractions.forEach(item => {
      this.addToIdList(item.id);
    });
  }

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

  onSelect(event) {

    this.selectedFiles = event.target.files;
    this.images = [];

    for (let i = 0; i < event.target.files.length; i++){
      var reader = new FileReader();
      reader.readAsDataURL(event.target.files[i]);
      reader.onload = (event: any) => {
        this.images.push(event.target.result);
      }
    }

  }

  imageClick(imageName: string) {
    let index = this.images.indexOf(imageName);
    this.selectedImage = imageName;
    this.templateForm.patchValue({
      'coverPhoto': this.selectedFiles[index].name
    })
  }

  isSelected(imageName: string) {
    return (imageName == this.selectedImage)
  }


  onSubmit(){
    this.tripName = this.templateForm.get('name').value

    this.tripService.createTemplate(this.templateForm.value).subscribe(
      response => {
        this.onResponse(response.message, 1);
        for(let i = 0; i < this.selectedFiles.length; i++){
          var formData = new FormData();
          formData.append('imageFile', this.selectedFiles[i]);

          this.tripService.upload(formData, this.tripName).subscribe(
            (res) => console.log(res),
            (err) => console.log(err)
          );
        }
      },
      err => {
        this.onResponse(err.error.message, 2);
      }
    );
    this.isSubmit = true;
  }

  onMessageClick(){
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

    this.templateForm.reset();
    this.selectedAttractions = [];
    this.idList = [];
    this.attractionList = [];
  }
  
  reloadPage(): void {
    window.location.reload();
  }
}
