import { Injectable } from '@angular/core';
import {Trip} from "./interfaces";

@Injectable({
  providedIn: 'root'
})
export class ApiDataService {
  baseURL = 'http://localhost:8080';

  constructor() { }
  
    async getTrips(): Promise<any[]> {
        const url = this.baseURL + '/trips';
        const data = await fetch(url, {method: 'GET'});
        return await data.json() ?? undefined;
    }

    async getTrip(id: number): Promise<Trip> {
        const url = this.baseURL + '/trips/' + id;
        const data = await fetch(url, {method: 'GET'});
        return await data.json() ?? undefined;
    }

    async getCities(): Promise<any[]> {
        const url = this.baseURL + '/cities';
        const data = await fetch(url, {method: 'GET'});
        return await data.json() ?? undefined;
    }

    async getTripsBy2Cities(city1: string, city2: string): Promise<Trip[]> {
        const url = `${this.baseURL}/trips/trips?city1=${city1}&city2=${city2}`;
        const data = await fetch(url, { method: 'GET' });
        return await data.json() ?? [];
      }

    async getTripPrice(tripId: number, numSeats: number, city1: string, city2: string, currency: string): Promise<number> {
        const url = `${this.baseURL}/trips/${tripId}/price/${city1}/${city2}/${numSeats}/${currency}`;
        const data = await fetch(url, { method: 'GET' });
        return await data.json() ?? 0;
    }

    async postReservation(name: string, trip: any, initialCity: any, finalCity: any, numSeats: number, totalPrice: number): Promise<any> {
    try {
      const url = this.baseURL + '/reservations';
      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, trip, initialCity, finalCity, numSeats, totalPrice })
      });

      // Check if the HTTP response status is in the 200-299 range (indicating success)
      if (response.ok) {
        // Parse the response body as JSON
        const data = await response.json();
        return data; // Return the response data
      } else {
        // If the response status is not in the success range, throw an error
        return await response.json(); // Return the error message
      }
    } catch (error) {
      // Catch any network-related errors (e.g., failed to connect)
      console.error('Error posting reservation:', error);
      return { status: '500' };
    }
  }


    async getReservations(): Promise<any[]> {
      const url = this.baseURL + '/reservations';
      const data = await fetch(url, {method: 'GET'});
      return await data.json() ?? undefined;
    }

    async getReservation(id: number): Promise<any> {
      const url = this.baseURL + '/reservations/' + id;
      const data = await fetch(url, {method: 'GET'});
      return await data.json() ?? undefined;
    }

    async getCurrencies(): Promise<any[]> {
      const url = this.baseURL + '/currency/all';
      const data = await fetch(url, {method: 'GET'});
      return await data.json() ?? undefined;
    }

  
    

}