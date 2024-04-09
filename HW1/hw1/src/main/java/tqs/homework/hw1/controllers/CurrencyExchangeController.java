package tqs.homework.hw1.controllers;

import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import tqs.homework.hw1.services.CurrencyExchangeService;

import java.util.Map;

import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@AllArgsConstructor
@RequestMapping("/currency")
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    @GetMapping("/rates")
    public ResponseEntity<Map<String,Double>> getAllCurrencyExchanges() {
        return ResponseEntity.ok(currencyExchangeService.getAllCurrencyExchanges());
    }

    @GetMapping("/{currency}")
    public ResponseEntity<Double> getCurrencyExchange(@PathVariable String currency) {
        return ResponseEntity.ok(currencyExchangeService.getExchangeRate(currency));
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllCurrencies() {
        return ResponseEntity.ok(currencyExchangeService.getAllCurrencies());
    }
    
}
