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

  constructor(private token: TokenStorageService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    if (this.token.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.token.getUser();
    }
  }

  showIncoming() {
    this.router.navigate(['incoming'], {relativeTo: this.route})
  }

  showPast() {
    this.router.navigate(['past'], {relativeTo: this.route})
  }

  showOrganized() {
    this.router.navigate(['organized'], {relativeTo: this.route})
  }

}
