import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, AbstractControl, FormControl  } from '@angular/forms';
import { isPast, add } from 'date-fns';
import { Attraction } from '../../../_model/attraction';
import { TripTemplate } from '../../../_model/tripTemplate';
import { TokenStorageService } from '../../../_services/token-storage.service';
import { TripService } from '../../../_services/trip.service';
import { CustomeDateValidators } from './from-to-date.validator'

@Component({
  selector: 'app-trip-creator',
  templateUrl: './trip-creator.component.html',
  styleUrls: ['./trip-creator.component.css']
})
export class TripCreatorComponent implements OnInit {

  constructor(
    private fb: FormBuilder, 
    private token: TokenStorageService,
    private tripService: TripService ) { }

  step_1 = true;
  step_2 = false;

  userTemplateList: TripTemplate[] = [];

  selectedTemplate: string;
  
  isSubmit = false;
  isFailed = false;
  isLoggedIn = false;

  message: string;
  goodResponse = false;
  badResponse = false;
  showMessage = true;

  DateNo1:Date;
  DateNo2:Date;
  isDateCorrect = false;
  isTried = false;
  
  currentUser: any;

  get startDate() {
    return this.createTrip.get('startDate');
  }

  get endDate() {
    return this.createTrip.get('endDate');
  }

  get userLimit() {
    return this.createTrip.get('userLimit');
  }

  set __templateName(name: string) {
    this.createTrip.setValue({
      templateName: name
    })
  }

  get f() { 
    return this.createTrip.controls; 
  }

  //jak zrobic zeby info o validatorze sie pokazywalo
  createTrip = this.fb.group({
    startDate: ['', Validators.required],
    endDate: ['', Validators.required],
    templateName: ['', Validators.required],
    userLimit: ['', [Validators.required, Validators.min(1)]],
  }, {
    validator: CustomeDateValidators.fromToDate('startDate', 'endDate')
  });

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();

      this.tripService.getTemplates().subscribe(
        (response: TripTemplate[]) => {
          this.userTemplateList = response;
        }
      )
    }
  }

  onSelect(name: string) {
    this.selectedTemplate = name;

    this.createTrip.patchValue({
      templateName: this.selectedTemplate
    })
  }

  isSelected(template_n: string): boolean {
    if( template_n == this.selectedTemplate) return true;
    else return false;
  }

  onSubmit() {
    this.checkDate();
    if (this.isDateCorrect){
      this.tripService.createTrip(this.createTrip.value).subscribe(
        response => {
          this.onResponse(response.message, 1);
        },
        err => {
          this.onResponse(err.error.message, 2);
        }
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

    this.createTrip.reset();
    this.selectedTemplate = "";
  }

  reloadPage(): void {
    window.location.reload();
  }

  checkDate():void{
    this.DateNo1 = add(new Date(this.startDate.value),{hours: 21, minutes: 59, seconds: 59});
    //this.DateNo2 = new Date(this.endDate.value);
    console.log(this.DateNo1);
    if (!isPast(this.DateNo1)){
      this.isDateCorrect = true;
    }
    this.isTried = true;
  }
}
