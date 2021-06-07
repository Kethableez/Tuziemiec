import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { appRoutingModule } from './app.routing';

import { AgmCoreModule } from '@agm/core';

import { authInterceptorProviders } from './_helpers/auth.interceptor';

import { HashLocationStrategy, LocationStrategy} from '@angular/common';

import { AppComponent } from './app.component';
import { RegisterComponent } from './register';
import { StartComponent } from './start';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './home/profile/profile.component';
import { TripCreatorComponent } from './home/trips/trip-creator/trip-creator.component';
import { AttractionComponent } from './home/attraction/attraction.component';
import { AttractionCreatorComponent } from './home/attraction/attraction-creator/attraction-creator.component';
import { TemplateCreatorComponent } from './home/templates/template-creator/template-creator.component';
import { TripHistoryComponent } from './trip-history/trip-history.component';
import { PastTripsComponent } from './trip-history/past-trips/past-trips.component';
import { IncomingTripsComponent } from './trip-history/incoming-trips/incoming-trips.component';
import { OrganizedTripsComponent } from './trip-history/organized-trips/organized-trips.component';
import { TripDetailComponent } from './home/trip-detail/trip-detail.component'
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { HomeComponent } from './home/home.component';
import { DashboardComponent } from './home/dashboard/dashboard.component';
import { TemplatesComponent } from './home/templates/templates.component';
import { TripsComponent } from './home/trips/trips.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    StartComponent,
    RegisterComponent,
    LoginComponent,
    ProfileComponent,
    TripCreatorComponent,
    AttractionComponent,
    AttractionCreatorComponent,
    TemplateCreatorComponent,
    TripHistoryComponent,
    PastTripsComponent,
    IncomingTripsComponent,
    OrganizedTripsComponent,
    TripDetailComponent,
    HomeComponent,
    DashboardComponent,
    TemplatesComponent,
    TripsComponent,
  ],

  imports: [
    HttpClientModule,
    BrowserModule,
    FormsModule,
    Ng2SearchPipeModule,
    ReactiveFormsModule,
    appRoutingModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyDrfFsVLrxbrj2ENXaMY08PvN7QTfP0EFs',
      libraries: ['places']
    }),
    NgbModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: environment.production,
      // Register the ServiceWorker as soon as the app is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
    
  ],

  providers: [authInterceptorProviders, {provide: LocationStrategy, useClass:HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }

