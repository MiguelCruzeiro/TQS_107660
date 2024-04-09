import { Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { ReservationComponent } from './reservation/reservation.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';

export const routes: Routes = [
    { path: '', component: HomepageComponent },
    { path: 'home', component: HomepageComponent },
    { path: 'reservation/:tripId/:initialCity/:finalCity', component: ReservationComponent },
    { path: 'confirmation/:reservationId', component: ConfirmationComponent },
    { path: '**', redirectTo: '' }
];
