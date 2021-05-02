import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AttractionService } from '../_services/attraction.service';
import { TokenStorageService } from '../_services/token-storage.service';

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
  });

  constructor(
    private token: TokenStorageService,
    private fb: FormBuilder,
    private attractionService: AttractionService) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
    }
  }

  onSubmit(){
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
