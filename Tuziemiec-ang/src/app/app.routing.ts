import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MainComponent } from './main/main.component';
import { MapsComponent } from './maps/maps.component';
import { ProfileComponent } from './profile/profile.component';

import { RegisterComponent } from './register';
import { StartComponent } from './start/start.component';
import { TripCreatorComponent } from './trip-creator/trip-creator.component';

const routes: Routes = [
    { path: 'register', component: RegisterComponent },
    { path: 'login', component: LoginComponent},
    { path: 'maps', component: MapsComponent},
    { path: 'profile', component: ProfileComponent},
    { path: 'main', component: MainComponent},
    { path: 'create', component: TripCreatorComponent},
    { path: '', component: StartComponent},
    { path: '**', redirectTo: ''}
];

export const appRoutingModule = RouterModule.forRoot(routes);