import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register';
import { StartComponent } from './start/start.component';

const routes: Routes = [
    { path: 'register', component: RegisterComponent },
    { path: '', component: StartComponent},
    { path: '**', redirectTo: ''},
];

export const appRoutingModule = RouterModule.forRoot(routes);