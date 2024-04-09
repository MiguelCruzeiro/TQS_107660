import { Component, Input, Output, inject, EventEmitter, OnChanges, SimpleChanges, OnInit} from '@angular/core';
import {ApiDataService} from "../api-data.service";
import {NgIf, NgFor} from "@angular/common";
import { FormsModule, FormGroup, FormBuilder, Validators, ReactiveFormsModule} from '@angular/forms';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {Trip} from "../interfaces";
import { DatePipe } from '@angular/common';





@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [NgIf, NgFor, FormsModule, ReactiveFormsModule, DatePipe, RouterLink], 
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  initialCity: string = '';
  finalCity: string = '';
  trips: Trip[] = [];
  cities: any[] = [];

  constructor(private apiDataService: ApiDataService, private router: Router) { }

  ngOnInit(): void {
    this.fetchCities();
  }

  fetchCities(): void {
    this.apiDataService.getCities().then(data => {
      this.cities = data;
    });
  }

  redirectReservation(tripId: number): void {
    // Redirect to reservation page with tripId
    this.router.navigate(['/reservation', tripId, this.initialCity, this.finalCity]);
  }

  getTrips(): void {
    this.apiDataService.getTripsBy2Cities(this.initialCity, this.finalCity)
      .then(data => {
        this.trips = data;
      });
  }
}

