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
class CityControllerIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetAllCities() {
        ResponseEntity<List<City>> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/cities",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {
                });

        List<City> cities = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(cities.isEmpty());
    }

    @Test
    void testGetCityById() {
        ResponseEntity<City> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/cities/1",
                HttpMethod.GET, null, new ParameterizedTypeReference<City>() {
                });

        City city = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, city.getId());
    }

    @Test
    void testGetCityByIdNotFound() {
        ResponseEntity<City> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/cities/9990",
                HttpMethod.GET, null, new ParameterizedTypeReference<City>() {
                });

        City city = response.getBody();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(city);
    }

    @Test
    void testCreateCity() {
        City city = new City(1L, "Porto");

        ResponseEntity<City> response = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/cities", city, City.class);

        City createdCity = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(city.getName(), createdCity.getName());
    }
    
}
