import { Routes, RouterModule } from '@angular/router';
import { AttractionCreatorComponent } from './home/attraction/attraction-creator/attraction-creator.component';
import { AttractionComponent } from './home/attraction/attraction.component';
import { HomeComponent } from './home/home.component';
import { IncomingTripsComponent } from './trip-history/incoming-trips/incoming-trips.component';
import { LoginComponent } from './login/login.component';
import { OrganizedTripsComponent } from './trip-history/organized-trips/organized-trips.component';
import { PastTripsComponent } from './trip-history/past-trips/past-trips.component';
import { ProfileComponent } from './home/profile/profile.component';

import { RegisterComponent } from './register';
import { StartComponent } from './start/start.component';
import { TemplateCreatorComponent } from './home/templates/template-creator/template-creator.component';
import { TripCreatorComponent } from './home/trips/trip-creator/trip-creator.component';
import { TripDetailComponent } from './trip-detail/trip-detail.component';
import { TripHistoryComponent } from './trip-history/trip-history.component';
import { DashboardComponent } from './home/dashboard/dashboard.component';
import { TripsComponent } from './home/trips/trips.component';
import { TemplatesComponent } from './home/templates/templates.component';

const routes: Routes = [
    { path: 'register', component: RegisterComponent },
    { path: 'login', component: LoginComponent},
    { path: 'home', component: HomeComponent,
        children: [
            { path: 'dashboard', component: DashboardComponent},
            { path: 'trips', component: TripsComponent},
            { path: 'create_trip', component: TripCreatorComponent},
            { path: 'trip/:id', component: TripDetailComponent},

            { path: 'templates', component: TemplatesComponent},
            { path: 'create_template', component: TemplateCreatorComponent},

            { path: 'attractions', component: AttractionComponent},
            { path: 'create_attraction', component: AttractionCreatorComponent},

            { path: 'profile', component: ProfileComponent},
            { path: 'panel', component: TripHistoryComponent,
                children: [
                    {path: 'incoming', component: IncomingTripsComponent},
                    {path: 'past', component: PastTripsComponent},
                    {path: 'organized', component: OrganizedTripsComponent},
                ]
            },
        ]
    },

    { path: '', component: StartComponent},
    { path: '**', redirectTo: ''}
];

export const appRoutingModule = RouterModule.forRoot(routes);