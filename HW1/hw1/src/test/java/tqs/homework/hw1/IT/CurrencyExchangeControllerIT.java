package tqs.homework.hw1.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CurrencyExchangeControllerIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetAllCurrencyExchanges() {
        ResponseEntity<Map<String,Double>> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/currency/rates",
                HttpMethod.GET, null, new ParameterizedTypeReference<Map<String,Double>>() {
                });

        Map<String,Double> currencyExchanges = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(currencyExchanges.isEmpty());

    }

    @Test
    void testGetCurrencyExchange() {
        ResponseEntity<Double> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/currency/USD",
                HttpMethod.GET, null, new ParameterizedTypeReference<Double>() {
                });

        Double currencyExchange = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(currencyExchange instanceof Double);
    }

    @Test
    void testGetAllCurrencies() {
        ResponseEntity<List<String>> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/currency/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
                });

        List<String> currencies = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(currencies.isEmpty());
    }

    
    
}
