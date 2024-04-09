package tqs.homework.hw1.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CurrencyExchangeService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeService.class);


    private static final String EXCHANGE_RATE_API_URL = "https://api.frankfurter.app/latest";
    private static final String RATES_STRING = "rates";

    
    private RestTemplate restTemplate;

    public CurrencyExchangeService() {
        this.restTemplate = new RestTemplate();
    }

    @Cacheable(value = "exchangeRates", key = "#root.methodName")
    public Map<String, Map<String, Object>> getExchangeRates() {
        @SuppressWarnings("unchecked")
        Map<String, Map<String,Object> > exchangeRates = restTemplate.getForObject(EXCHANGE_RATE_API_URL, Map.class);
        return exchangeRates;
    }

    @Cacheable(value = "exchangeRates", key = "#root.methodName")
    public Map<String, Double> getAllCurrencyExchanges() {
        @SuppressWarnings("unchecked")
        Map<String, Map<String, Object>> exchangeRates = restTemplate.getForObject(EXCHANGE_RATE_API_URL, Map.class);
        if (exchangeRates == null) {
            return new HashMap<>();
        }
        Map<String, Double> rates = new HashMap<>();
        Map<String, Object> ratesMap = exchangeRates.get(RATES_STRING);
        for (Map.Entry<String, Object> entry : ratesMap.entrySet()) {
            String currency = entry.getKey();
            Object rateObject = entry.getValue();
            if (rateObject instanceof Number number) {
                rates.put(currency, (number).doubleValue());
            } else {
                logger.error("Non-numeric value found for currency {}: {}", currency, rateObject);
            }
        }
        return rates;
    }

    //get the exchange rate for a specific currency
    @Cacheable(value = "exchangeRates", key = "#root.methodName + #currency")
    public Double getExchangeRate(String currency) {
        Map<String, Map<String, Object>> exchangeRates = getExchangeRates();
        if (exchangeRates == null) {
            return null;
        }
        Map<String, Object> ratesMap = exchangeRates.get(RATES_STRING);
        Object rateObject = ratesMap.get(currency);
        if (rateObject instanceof Number number) {
            return (number).doubleValue();
        } else {
            logger.error("Non-numeric value found for currency {}: {}", currency, rateObject);
            return null;
        }
    }

    //get all the currencies available
    @Cacheable(value = "exchangeRates", key = "#root.methodName")
    public List<String> getAllCurrencies() {
        Map<String, Map<String, Object>> exchangeRates = getExchangeRates();
        if (exchangeRates == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(exchangeRates.get(RATES_STRING).keySet());
    }

    @Scheduled(fixedRate = 300000) // 5 minutes
    @CacheEvict(value = {"exchangeRates"}, allEntries = true)
    public void cacheRefresh() {
        // Clear the cache
    }

}
