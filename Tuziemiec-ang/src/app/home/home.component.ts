import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../_model/user';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  isLoggedIn = false;
  currentUser: any;
  User: User;
  url_route: String;

  dashboardClicked = false;
  tripClicked = false;
  templateClicked = false;
  attractionClicked = false;
  panelClicked = false;
  profileClicked = false;

  constructor(
    private token: TokenStorageService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router) { }



  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }

    if (this.isLoggedIn == true) {
      this.userService.getData().subscribe(
        (response: User) => {
          this.User = response;
        }
      )
    }

    this.url_route = this.route.snapshot['_routerState'].url;

    if (this.url_route.includes("/home/dashboard")) {
      this.dashboardClicked = true;
    }

    else if (this.url_route.includes("/home/trips")) {
      this.tripClicked = true;
    }

    else if (this.url_route.includes("/home/templates")) {
      this.templateClicked = true;
    }

    else if (this.url_route.includes("/home/attractions")) {
      this.attractionClicked = true;
    }

    else if (this.url_route.includes("/home/panel")) {
      this.panelClicked = true;
    }

    else if (this.url_route.includes("/home/profile")) {
      this.profileClicked = true;
    }

  }


  onButtonGroupClick($event) {
    let clickedElement = $event.target || $event.srcElement;

    if (clickedElement.nodeName === "button") {

      let isCertainButtonAlreadyActive = clickedElement.parentElement.querySelector(".active");
      if (isCertainButtonAlreadyActive) {
        isCertainButtonAlreadyActive.classList.remove("active");
      }
      clickedElement.className += " active";
    }
  }

  showDashboard() {
    this.dashboardClicked = true;
    this.tripClicked = false;
    this.templateClicked = false;
    this.attractionClicked = false;
    this.profileClicked = false;
    this.panelClicked = false;

    this.router.navigate(['dashboard'], {relativeTo: this.route});
  }

  showTrips() {
    this.tripClicked = true;
    this.dashboardClicked = false;
    this.templateClicked = false;
    this.attractionClicked = false;
    this.profileClicked = false;
    this.panelClicked = false;

    this.router.navigate(['trips'], { relativeTo: this.route })
  }

  showTemplates() {
    this.templateClicked = true;
    this.tripClicked = false;
    this.dashboardClicked = false;
    this.attractionClicked = false;
    this.profileClicked = false;
    this.panelClicked = false;

    this.router.navigate(['templates'], {relativeTo: this.route})
  }

  showAttractions(){
    this.attractionClicked = true;
    this.profileClicked = false;
    this.tripClicked = false;
    this.dashboardClicked = false;
    this.templateClicked = false;
    this.panelClicked = false;

    this.router.navigate(['attractions'], { relativeTo: this.route});
  }

  showProfile() {
    this.profileClicked = true;
    this.tripClicked = false;
    this.dashboardClicked = false;
    this.templateClicked = false;
    this.attractionClicked = false;
    this.panelClicked = false;

    this.router.navigate(['profile'], { relativeTo: this.route});
  }

  showPanel() {
    this.panelClicked = true;
    this.profileClicked = false;
    this.tripClicked = false;
    this.dashboardClicked = false;
    this.templateClicked = false;
    this.attractionClicked = false;

    this.router.navigate(['panel'], {relativeTo: this.route});
  }


  logout(): void {
    this.token.signOut();
  }
}
