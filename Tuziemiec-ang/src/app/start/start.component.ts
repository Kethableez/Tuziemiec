import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { TripService } from '../_services/trip.service';

@Component({ 
    selector: 'app-start',
    templateUrl: 'start.component.html',
    styleUrls: ['start.component.css']
})
export class StartComponent implements OnInit {
    
    isLoggedIn = false;

    constructor(
        private token: TokenStorageService,
        private router: Router) {}
    
    ngOnInit(): void {
        if (this.token.getToken()) {
            this.isLoggedIn = true;
            this.router.navigate(['/home/dashboard']);
          }
    }

}