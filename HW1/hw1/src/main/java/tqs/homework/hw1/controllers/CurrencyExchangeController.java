package tqs.homework.hw1.controllers;

import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import tqs.homework.hw1.services.CurrencyExchangeService;

import java.util.Map;

import java.util.List;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@AllArgsConstructor
@RequestMapping("/currency")
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    @Operation(summary = "Get all currency exchange rates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all currency exchange rates")
    })
    @GetMapping("/rates")
    public ResponseEntity<Map<String,Double>> getAllCurrencyExchanges() {
        return ResponseEntity.ok(currencyExchangeService.getAllCurrencyExchanges());
    }

    @Operation(summary = "Get currency exchange rate")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the currency exchange rate")
    })
    @GetMapping("/{currency}")
    public ResponseEntity<Double> getCurrencyExchange(@PathVariable String currency) {
        return ResponseEntity.ok(currencyExchangeService.getExchangeRate(currency));
    }

    @Operation(summary = "Get all currencies")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all currencies")
    })
    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllCurrencies() {
        return ResponseEntity.ok(currencyExchangeService.getAllCurrencies());
    }
    
}
