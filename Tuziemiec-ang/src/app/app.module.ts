import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { appRoutingModule } from './app.routing';

import { AgmCoreModule } from '@agm/core';

import { authInterceptorProviders } from './_helpers/auth.interceptor';

import { AppComponent } from './app.component';
import { RegisterComponent } from './register';
import { StartComponent } from './start';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { MainComponent } from './main/main.component';
<<<<<<< HEAD
import { TripCreatorComponent } from './trip-creator/trip-creator.component';
=======
import { MapsComponent } from './maps/maps.component';

>>>>>>> 18643e5594a244c5d16939b623474cc6509c9208
 
@NgModule({
  declarations: [
    AppComponent,
    StartComponent,
    RegisterComponent,
    LoginComponent,
    ProfileComponent,
    MainComponent,
<<<<<<< HEAD
    TripCreatorComponent
=======
    MapsComponent
>>>>>>> 18643e5594a244c5d16939b623474cc6509c9208
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    appRoutingModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyCr68faOi-O2mIfX7Rs7jSDXNhogTkvhW4'
    })
  ],

  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }

