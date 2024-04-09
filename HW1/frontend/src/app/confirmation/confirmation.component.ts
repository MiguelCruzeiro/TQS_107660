import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgIf, NgFor } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiDataService } from '../api-data.service';
import { Trip } from '../interfaces';
import { MatCardModule } from '@angular/material/card'; // Import MatCardModule
import { Title } from '@angular/platform-browser';



@Component({
  selector: 'app-confirmation',
  standalone: true,
  imports: [ NgIf, NgFor, FormsModule, ReactiveFormsModule, DatePipe, RouterLink, MatCardModule],
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent implements OnInit {
  reservationId: number = 0;
  reservation: any = {};
  initialCity: any = '';
  finalCity: any = '';
  name: string = '';
  numSeats: number = 0;
  trip: any = {};
  totalPrice: number = 0;

  constructor(private route: ActivatedRoute, private router: Router, private apiDataService: ApiDataService, private titleService: Title) {


   }

  ngOnInit(): void {
    this.titleService.setTitle('Reservation Confirmation');
    this.route.params.subscribe(params => {
      this.reservationId = params['reservationId'];
      this.apiDataService.getReservation(this.reservationId).then(data => {
        this.reservation = data;
        this.initialCity = data.initialCity;
        this.finalCity = data.finalCity;
        this.name = data.name;
        this.numSeats = data.numSeats;
        this.totalPrice = data.totalPrice;
        this.apiDataService.getTrip(data.trip.id).then(data => {
          this.trip = data;
        });
      } );
    });
  }
}

