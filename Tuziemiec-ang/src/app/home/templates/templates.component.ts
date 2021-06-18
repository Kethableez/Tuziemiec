import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { TripTemplate } from 'src/app/_model/tripTemplate';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { TripService } from 'src/app/_services/trip.service';

@Component({
  selector: 'app-templates',
  templateUrl: './templates.component.html',
  styleUrls: ['./templates.component.css']
})
export class TemplatesComponent implements OnInit {

  currentUser: any;
  userTemplateList: TripTemplate[];
  templateSelector: TripTemplate;
  templateClicked = false;

  message: string;
  goodResponse = false;
  badResponse = false;
  showMessage = true;

  selectedFiles = FileList;

  constructor(
    private token: TokenStorageService,
    private tripService: TripService,
    private fb: FormBuilder) { }

    editDescriptionForm = this.fb.group({
      description: ['', Validators.required],
    });

  ngOnInit(): void {
    this.currentUser = this.token.getUser();

    this.tripService.getTemplates().subscribe(
      (response: TripTemplate[]) => {
        this.userTemplateList = response;
      }
    )
  }

  onFormSubmit(id: number){
    this.tripService.editTemplate(this.editDescriptionForm.value, id).subscribe(
      (res) => this.onResponse(res.message, 1),
      (err) => this.onResponse(err.error.message, 2)
    )
  }

  onSelectFile(event){
    this.selectedFiles = event.target.files;
    console.log(this.selectedFiles.length)
  }

  openEditor (template: TripTemplate) {
    this.templateSelector = template;
    this.templateClicked = true;
  }

  closeEditor() {
    this.templateSelector = null;
    this.templateClicked = false;
  }

  onUpload(name: string) {
    for(let i = 0; i < this.selectedFiles.length; i++){
      var formData = new FormData();
      formData.append('imageFile', this.selectedFiles[i]);

      this.tripService.upload(formData, name).subscribe(
        (res) => this.onResponse(res.message, 1),
        (err) => this.onResponse(err.error.message, 2)
      );
    }
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
  }



  getPhoto(tripName: string, fileName: string): string {
    return "http://http://virt5.iiar.pwr.edu.pl:8089/images/getTripPhoto?fileName=" + fileName + "&tripName=" + tripName;
  }


}
