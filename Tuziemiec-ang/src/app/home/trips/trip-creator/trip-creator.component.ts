import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, AbstractControl, FormControl  } from '@angular/forms';
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

  selectedTemplate = "none";
  
  isSubmit = false;
  isFailed = false;
  isLoggedIn = false;

  message: string;
  currentUser: any;

  get startDate() {
    return this.createTrip.get('startDate');
  }

  get endDate() {
    return this.createTrip.get('endDate');
  }

  // get templateName() {
  //   return this.createTrip.get('templateName');
  // }

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
    templateName: [this.selectedTemplate],
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

  onSelect(name: string): any {
    return this.selectedTemplate = name;
  }

  onClick() {
    this.createTrip.patchValue({
      templateName: this.selectedTemplate
    })
    this.step_1 = false;
    this.step_2 = true;
  }

  isSelected(template_n: string): boolean {
    if( template_n == this.selectedTemplate) return true;
    else return false;
  }

  onSubmit() {
    this.tripService.createTrip(this.createTrip.value).subscribe(
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
