package tqs.homework.hw1.controllers;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;
import tqs.homework.hw1.models.City;
import tqs.homework.hw1.services.CityService;

import java.net.URI;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@AllArgsConstructor
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @Operation(summary = "Get city by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the city"),
        @ApiResponse(responseCode = "404", description = "City not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        City city = cityService.getCityById(id);
        if (city == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(city);
    }

    @Operation(summary = "Save city")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "City created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<City> saveCity(@RequestBody City city) {
        City savedCity = cityService.saveCity(city);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCity.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedCity);
    }

    @Operation(summary = "Get all cities")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all cities")
    })
    @GetMapping
    public ResponseEntity<Iterable<City>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }
}
