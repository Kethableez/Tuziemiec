import { Routes, RouterModule } from '@angular/router';
import { AttractionCreatorComponent } from './attraction-creator/attraction-creator.component';
import { AttractionComponent } from './attraction/attraction.component';
import { IncomingTripsComponent } from './incoming-trips/incoming-trips.component';
import { LoginComponent } from './login/login.component';
import { MainComponent } from './main/main.component';
import { MapsComponent } from './maps/maps.component';
import { OrganizedTripsComponent } from './organized-trips/organized-trips.component';
import { PastTripsComponent } from './past-trips/past-trips.component';
import { ProfileComponent } from './profile/profile.component';

import { RegisterComponent } from './register';
import { StartComponent } from './start/start.component';
import { TemplateCreatorComponent } from './template-creator/template-creator.component';
import { TripCreatorComponent } from './trip-creator/trip-creator.component';
import { TripDetailComponent } from './trip-detail/trip-detail.component';
import { TripPanelComponent } from './trip-panel/trip-panel.component';

const routes: Routes = [
    { path: 'register', component: RegisterComponent },
    { path: 'login', component: LoginComponent},
    { path: 'maps', component: MapsComponent},
    { path: 'attractions', component: AttractionComponent},
    { path: 'create_attraction', component: AttractionCreatorComponent},
    { path: 'create_template', component: TemplateCreatorComponent},
    { path: 'panel', component: TripPanelComponent,
        children: [
            {path: 'incoming', component: IncomingTripsComponent},
            {path: 'past', component: PastTripsComponent},
            {path: 'organized', component: OrganizedTripsComponent},
        ]
    },
    { path: 'profile', component: ProfileComponent},
    { path: 'trip/:id', component: TripDetailComponent},
    { path: 'main', component: MainComponent},
    { path: 'create_trip', component: TripCreatorComponent},
    { path: '', component: StartComponent},
    { path: '**', redirectTo: ''}
];

export const appRoutingModule = RouterModule.forRoot(routes);