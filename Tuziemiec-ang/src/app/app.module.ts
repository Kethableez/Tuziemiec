import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { appRoutingModule } from './app.routing';

//import { authInterceptorProviders } from './_helpers/auth.interceptor';

import { AppComponent } from './app.component';
import { RegisterComponent } from './register';
import { StartComponent } from './start';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { MainComponent } from './main/main.component';
 
@NgModule({
  declarations: [
    AppComponent,
    StartComponent,
    RegisterComponent,
    LoginComponent,
    ProfileComponent,
    MainComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    appRoutingModule
  ],

  providers: [/*authInterceptorProviders*/],
  bootstrap: [AppComponent]
})
export class AppModule { }

