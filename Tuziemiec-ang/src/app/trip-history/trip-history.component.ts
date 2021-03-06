import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-trip-history',
  templateUrl: './trip-history.component.html',
  styleUrls: ['./trip-history.component.css']
})
export class TripHistoryComponent implements OnInit {
  isLoggedIn = false;
  currentUser: any;

  incomingClicked = false;
  pastClicked = false;
  organisedClicked = false;

  url_route: String;


  constructor(private token: TokenStorageService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }

    this.url_route = this.route.snapshot['_routerState'].url;

    if (this.url_route == "/home/panel/incoming") this.incomingClicked = true;
    else if (this.url_route == "/home/panel/past") this.pastClicked = true;
    else if (this.url_route == "/home/panel/organized") this.organisedClicked = true;
  }

  showIncoming() {
    this.incomingClicked = true;
    this.organisedClicked = false;
    this.pastClicked = false;
    this.router.navigate(['incoming'], {relativeTo: this.route})
  }

  showPast() {
    this.incomingClicked = false;
    this.organisedClicked = false;
    this.pastClicked = true;
    this.router.navigate(['past'], {relativeTo: this.route})
  }

  showOrganized() {
    this.incomingClicked = false;
    this.organisedClicked = true;
    this.pastClicked = false;
    this.router.navigate(['organized'], {relativeTo: this.route})
  }

}
