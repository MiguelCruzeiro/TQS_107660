import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgIf, NgFor } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiDataService } from '../api-data.service';
import { Trip } from '../interfaces';
import { MatCardModule } from '@angular/material/card'; // Import MatCardModule


@Component({
  selector: 'app-reservation',
  standalone: true,
  templateUrl: './reservation.component.html',
  imports: [NgIf, NgFor, FormsModule, ReactiveFormsModule, DatePipe, RouterLink, MatCardModule], 
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {
  tripId: number = 0;
  initialCity: any = '';
  finalCity: any = '';
  name: string = '';
  numSeats: number = 0;
  trip: any = {};
  totalPrice: number = 0;
  reservationId: number = 0;
  currencies: any = [];
  currency: any = "EUR";

  constructor(private route: ActivatedRoute, private router: Router, private apiDataService: ApiDataService) {


   }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.tripId = params['tripId'];
      this.initialCity = params['initialCity'];
      this.finalCity = params['finalCity'];
      console.log('Trip ID:', this.tripId);
      console.log('Initial City:', this.initialCity);
      console.log('Final City:', this.finalCity);
      this.apiDataService.getTrip(this.tripId).then(data => {
        this.trip = data;
      });
      this.apiDataService.getCurrencies().then(data => {
        this.currencies = data; 
        console.log('Currencies:', this.currencies);
      });
    });
  }

  updatePrice(event: any): void {
    // Assuming there's an API call to fetch the updated price based on the number of seats
    // Replace 'updatePriceApiCall' with your actual API endpoint
    this.apiDataService.getTripPrice(this.tripId, this.numSeats, this.initialCity, this.finalCity, this.currency).then((price: number) => {
      this.totalPrice = price;
      console.log('Total Price:', this.totalPrice);
    });
  }

  submitReservation(): void {
    // Here, you can implement your reservation logic
    // For example, you can send a reservation request to your backend API
    // with the tripId, name, and numSeats
    console.log('Name:', this.name);
    console.log('Number of Seats:', this.numSeats);
    console.log('Initial City:', this.initialCity);
    console.log('Final City:', this.finalCity);
    this.apiDataService.postReservation(this.name, this.trip, this.initialCity, this.finalCity, this.numSeats, this.totalPrice).then(response => {
      console.log('Reservation Response:', response);
      this.reservationId = response.id;
      if (response.status === '500' || response.status === '400') {
        // Show an error message to the user
        alert('Bad Request: Please check your reservation details and try again.');
      }
      else {
        // Show a success message to the user
        this.router.navigate(['/confirmation', this.reservationId]); // Redirect to the confirmation page
      }
    } );
    // After successful reservation, you can redirect the user to a confirmation page or any other page
  }
}
