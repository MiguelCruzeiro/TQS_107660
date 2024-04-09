package tqs.homework.hw1.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;


import java.util.List;

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

import tqs.homework.hw1.models.*;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TripControllerIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetAllTrips() {
        ResponseEntity<List<Trip>> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/trips/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Trip>>() {
                });

        List<Trip> trips = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(trips.isEmpty());
    }

    @Test
    void testGetTripById() {
        ResponseEntity<Trip> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/trips/1",
                HttpMethod.GET, null, new ParameterizedTypeReference<Trip>() {
                });

        Trip trip = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, trip.getId());
    }

    @Test
    void testGetTripByIdNotFound() {
        ResponseEntity<Trip> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/trips/200",
                HttpMethod.GET, null, new ParameterizedTypeReference<Trip>() {
                });

        Trip trip = response.getBody();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(trip);
    }

    @Test
    void testGetTripsByCities() {
        ResponseEntity<List<Trip>> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/trips/trips?city1=Porto&city2=Lisboa",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Trip>>() {
                });

        List<Trip> trips = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(trips.isEmpty());
    }

    @Test
    void testGetTripsByCitiesNotFound() {
        ResponseEntity<List<Trip>> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/trips/trips?city1=Porto&city2=Madrid",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Trip>>() {
                });

        List<Trip> trips = response.getBody();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(trips);
    }

    @Test
    void testGetTripsByCitiesBadRequest() {
        ResponseEntity<List<Trip>> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/trips/trips?city1=Porto&city2=",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Trip>>() {
                });

        List<Trip> trips = response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(trips);

    }

    @Test
    void testGetTripsByCitiesBadRequest2() {
        ResponseEntity<List<Trip>> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/trips/trips?city1=&city2=Lisboa",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Trip>>() {
                });

        List<Trip> trips = response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(trips);

    }

    @Test
    void testGetTripPrice() {
        ResponseEntity<Double> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/trips/1/price/Porto/Lisboa/1/EUR",
                HttpMethod.GET, null, new ParameterizedTypeReference<Double>() {
                });

        Double price = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10.0, price);
    }

    @Test
    void testGetTripPriceNotFound() {
        ResponseEntity<Double> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/trips/200/price/Porto/Lisboa/1/EUR",
                HttpMethod.GET, null, new ParameterizedTypeReference<Double>() {
                });

        Double price = response.getBody();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(price);
    }

    @Test
    void testGetTripPriceBadRequest() {
        ResponseEntity<Double> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/trips/1/price/Porto/Lisboa/1/1",
                HttpMethod.GET, null, new ParameterizedTypeReference<Double>() {
                });

        Double price = response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(price);
    }

    @Test
    void testGetTripPriceBadRequest2() {
        ResponseEntity<Double> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/trips/1/price/Porto/Lisboa/-1/EUR",
                HttpMethod.GET, null, new ParameterizedTypeReference<Double>() {
                });

        Double price = response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(price);
    }


    
}
