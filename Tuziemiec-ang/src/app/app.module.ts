import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { appRoutingModule } from './app.routing';

import { AppComponent } from './app.component';
import { RegisterComponent } from './register';
import { StartComponent } from './start';
 
@NgModule({
  declarations: [
    AppComponent,
    StartComponent,
    RegisterComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    appRoutingModule
  ],

  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

