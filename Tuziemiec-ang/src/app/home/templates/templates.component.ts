import { Component, OnInit } from '@angular/core';
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

  constructor(
    private token: TokenStorageService,
    private tripService: TripService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();

    this.tripService.getTemplates().subscribe(
      (response: TripTemplate[]) => {
        this.userTemplateList = response;
      }
    )
  }

}
