package tqs.homework.hw1.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;
import tqs.homework.hw1.models.*;
import tqs.homework.hw1.services.CityService;
import tqs.homework.hw1.services.TripService;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@AllArgsConstructor
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;
    private final CityService cityService;

    @Operation(summary = "Get trip by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the trip"),
        @ApiResponse(responseCode = "404", description = "Trip not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        if (trip == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(trip);
    }

    @Operation(summary = "Save trip")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Trip created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Trip> saveTrip(@RequestBody Trip trip) {
        Trip savedTrip = tripService.saveTrip(trip);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTrip.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedTrip);
    }

    @Operation(summary = "Get all trips")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all trips")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Trip>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @Operation(summary = "Get trips between cities")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the trips"),
        @ApiResponse(responseCode = "404", description = "City not found")
    })
    @GetMapping("/trips")
    public ResponseEntity<List<Trip>> getTripsThroughCities(
               @RequestParam(name = "city1") String city1Name, 
               @RequestParam(name = "city2") String city2Name) {

        if (city2Name.equals("") || city1Name.equals("")) {
            return ResponseEntity.badRequest().build();
        }

        City city1 = cityService.getCityByName(city1Name);
        City city2 = cityService.getCityByName(city2Name);

        if (city1 == null || city2 == null) {
            return ResponseEntity.notFound().build();
        }


        List<Trip> trips = tripService.getTripsByCities(city1, city2);
        return ResponseEntity.ok(trips);
    }

    @Operation(summary = "Get trip price")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the trip price"),
        @ApiResponse(responseCode = "404", description = "Trip not found")
    })
    @GetMapping("/{id}/price/{city1}/{city2}/{numSeats}/{currency}")
    public ResponseEntity<Double> getTripPrice(
               @PathVariable Long id,
               @PathVariable String city1,
               @PathVariable String city2,
               @PathVariable int numSeats,
               @PathVariable String currency) {

        City city1Obj = cityService.getCityByName(city1);
        City city2Obj = cityService.getCityByName(city2);


        if (city1Obj == null || city2Obj == null) {
            return ResponseEntity.notFound().build();
        }
    
        if (numSeats <= 0 || !(currency.matches("[a-zA-Z]+"))) {
            return ResponseEntity.badRequest().build();
        }

        Trip trip = tripService.getTripById(id);

        if (trip == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tripService.getPriceBetweenCities(trip, city1Obj, city2Obj, currency) * numSeats);
    }
    
}
