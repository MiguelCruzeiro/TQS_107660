package tqs.homework.hw1.Service;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import tqs.homework.hw1.services.CurrencyExchangeService;

@ExtendWith(MockitoExtension.class)
class CurrencyExchangeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    @BeforeEach
    public void setUp() {
        Map<String, Object> exchangeRates = new HashMap<>();
        exchangeRates.put("amount", 1.0);
        exchangeRates.put("base", "EUR");
        exchangeRates.put("date", "2024-04-05");
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0841);
        rates.put("EUR", 1.0);
        rates.put("GBP", 0.85773);
        exchangeRates.put("rates", rates);

        when(restTemplate.getForObject("https://api.frankfurter.app/latest", Map.class))
                .thenReturn(exchangeRates);
    }

    @Test
    void testGetExchangeRates() {
        Map<String, Object> expectedExchangeRates = new HashMap<>();
        expectedExchangeRates.put("amount", 1.0);
        expectedExchangeRates.put("base", "EUR");
        expectedExchangeRates.put("date", "2024-04-05");
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0841);
        rates.put("EUR", 1.0);
        rates.put("GBP", 0.85773);
        expectedExchangeRates.put("rates", rates);

        assertEquals(expectedExchangeRates, currencyExchangeService.getExchangeRates());
    }

    @Test
    void testGetAllCurrencyExchanges() {
        Map<String, Double> expectedRates = new HashMap<>();
        expectedRates.put("USD", 1.0841);
        expectedRates.put("EUR", 1.0);
        expectedRates.put("GBP", 0.85773);

        assertEquals(expectedRates, currencyExchangeService.getAllCurrencyExchanges());
    }

    @Test
    void testGetExchangeRate() {
        assertEquals(1.0, currencyExchangeService.getExchangeRate("EUR"));
        assertEquals(1.0841, currencyExchangeService.getExchangeRate("USD"));
        assertEquals(0.85773, currencyExchangeService.getExchangeRate("GBP"));
    }

    @Test
    void testGetAllCurrencyExchangesEmpty() {
        Map<String, Object> exchangeRates = new HashMap<>();
        exchangeRates.put("amount", 1.0);
        exchangeRates.put("base", "EUR");
        exchangeRates.put("date", "2024-04-05");
        Map<String, Object> rates = new HashMap<>();
        exchangeRates.put("rates", rates);

        when(restTemplate.getForObject("https://api.frankfurter.app/latest", Map.class))
                .thenReturn(exchangeRates);

        assertEquals(new HashMap<>(), currencyExchangeService.getAllCurrencyExchanges());
    }

    @Test
    void testGetExchangeRateNull() {
        when(restTemplate.getForObject("https://api.frankfurter.app/latest", Map.class))
                .thenReturn(null);

        assertEquals(null, currencyExchangeService.getExchangeRate("EUR"));
        assertEquals(null, currencyExchangeService.getExchangeRate("USD"));
        assertEquals(null, currencyExchangeService.getExchangeRate("GBP"));
    }

    @Test
    void testGetExchangeRateNonNumeric() {
        Map<String, Object> exchangeRates = new HashMap<>();
        exchangeRates.put("amount", 1.0);
        exchangeRates.put("base", "EUR");
        exchangeRates.put("date", "2024-04-05");
        Map<String, Object> rates = new HashMap<>();
        rates.put("USD", "1.0841");
        rates.put("EUR", 1.0);
        rates.put("GBP", 0.85773);
        exchangeRates.put("rates", rates);

        when(restTemplate.getForObject("https://api.frankfurter.app/latest", Map.class))
                .thenReturn(exchangeRates);

        assertEquals(null, currencyExchangeService.getExchangeRate("USD"));
    }

    @Test
    void testGetAllCurrenciesEmpty() {
        Map<String, Object> exchangeRates = new HashMap<>();
        exchangeRates.put("amount", 1.0);
        exchangeRates.put("base", "EUR");
        exchangeRates.put("date", "2024-04-05");
        Map<String, Object> rates = new HashMap<>();
        exchangeRates.put("rates", rates);

        when(restTemplate.getForObject("https://api.frankfurter.app/latest", Map.class))
                .thenReturn(exchangeRates);

        assertEquals(new HashMap<>(), currencyExchangeService.getAllCurrencyExchanges());
    }

    

}
