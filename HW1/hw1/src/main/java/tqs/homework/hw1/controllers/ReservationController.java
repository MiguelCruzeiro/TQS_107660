package tqs.homework.hw1.controllers;

import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import tqs.homework.hw1.models.*;
import tqs.homework.hw1.services.CityService;
import tqs.homework.hw1.services.ReservationService;
import tqs.homework.hw1.services.TripService;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@AllArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final TripService tripService;
    private final CityService cityService;

    @Operation(summary = "Get reservation by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the reservation"),
        @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    @Operation(summary = "Save reservation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reservation created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Reservation> saveReservation(@RequestBody Reservation reservation) {
        Trip trip = tripService.getTripById(reservation.getTrip().getId());
        if (trip == null) {
            return ResponseEntity.notFound().build();
            
        }
        reservation.setTrip(trip);
        City initialCity = cityService.getCityByName(reservation.getInitialCity().getName());
        reservation.setInitialCity(initialCity);
        City finalCity = cityService.getCityByName(reservation.getFinalCity().getName());
        reservation.setFinalCity(finalCity);
        if (reservation.getInitialCity() == null || reservation.getFinalCity() == null) {
            return ResponseEntity.badRequest().build();
        }
        if (reservation.getInitialCity().getId() == reservation.getFinalCity().getId()) {
            return ResponseEntity.badRequest().build();
        }
        //inicial city and final city must be in the trip cities
        if (!trip.getCities().contains(initialCity) || !trip.getCities().contains(finalCity)) {
            return ResponseEntity.badRequest().build();
        }
        //inicial city must be before final city
        if (trip.getCities().indexOf(initialCity) > trip.getCities().indexOf(finalCity)) {
            return ResponseEntity.badRequest().build();
        }
        if (trip.getAvailableSeats() < reservation.getNumSeats()) {
            return ResponseEntity.badRequest().build();
        }
        if (reservation.getNumSeats() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (reservation.getName().equals("")) {
            return ResponseEntity.badRequest().build();
        }

        // Save the reservation with calculated total price
        Reservation savedReservation = reservationService.saveReservation(reservation);

        return ResponseEntity.created(null).body(savedReservation);
    }


    @Operation(summary = "Get all reservations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all reservations")
    })
    @GetMapping
    public ResponseEntity<Iterable<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }


    
}
