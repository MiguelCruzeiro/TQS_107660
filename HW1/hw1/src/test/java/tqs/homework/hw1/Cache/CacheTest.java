package tqs.homework.hw1.Cache;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
class CacheTest {

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
    void testCacheUsage() {

        Map<String, Double> result1 = currencyExchangeService.getAllCurrencyExchanges();
        assertThat(result1).containsEntry("EUR", 1.00);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Map.class));

        Map<String, Double> result2 = currencyExchangeService.getAllCurrencyExchanges();
        assertThat(result2).containsEntry("EUR", 1.00);
        verifyNoMoreInteractions(restTemplate); 
    }

    @Test
    void testCacheGetExchangeRate() {
        Double result1 = currencyExchangeService.getExchangeRate("USD");
        assertThat(result1).isEqualTo(1.0841);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Map.class));

        Double result2 = currencyExchangeService.getExchangeRate("USD");
        assertThat(result2).isEqualTo(1.0841);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testCacheGetAllCurrencies() {
        currencyExchangeService.getAllCurrencies();
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Map.class));

        currencyExchangeService.getAllCurrencies();
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testCacheEviction() {
        Map<String, Double> result1 = currencyExchangeService.getAllCurrencyExchanges();
        assertThat(result1).containsEntry("EUR", 1.00);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Map.class));

        Map<String, Double> result2 = currencyExchangeService.getAllCurrencyExchanges();
        assertThat(result2).containsEntry("EUR", 1.00);
        verify(restTemplate, times(2)).getForObject(anyString(), eq(Map.class));
    }
    
}
