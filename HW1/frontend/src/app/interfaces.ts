export interface Trip {
    id: number;
    departureDateTime: string;
    basePrice: number;
    seats: number;
    availableSeats: number;
    cities: any[]; // Update the type as per your City model
  }