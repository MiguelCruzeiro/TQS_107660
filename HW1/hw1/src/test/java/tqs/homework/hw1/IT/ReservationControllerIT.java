package tqs.homework.hw1.IT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tqs.homework.hw1.models.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationControllerIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        // Setup code here if needed
    }

    @Test
    void testGetAllReservations() {
        ResponseEntity<List<Reservation>> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/reservations",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Reservation>>() {});

        List<Reservation> reservations = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(reservations.isEmpty());
    }

    @Test
    void testGetReservationById() {
        ResponseEntity<Reservation> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/reservations/1",
                HttpMethod.GET, null, new ParameterizedTypeReference<Reservation>() {});

        Reservation reservation = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, reservation.getId());
    }

    @Test
    void testGetReservationByInvalidId() {
        ResponseEntity<Reservation> response = restTemplate.exchange("http://localhost:" + randomServerPort + "/reservations/100",
                HttpMethod.GET, null, new ParameterizedTypeReference<Reservation>() {});

        Reservation reservation = response.getBody();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(reservation);
    }

    @Test
    void testCreateReservation() {
        Trip trip = new Trip(1L, LocalDateTime.now(), 10.0, 100);

        City city1 = new City(1L, "Porto");
        City city2 = new City(2L, "Lisboa");

        trip.getCities().add(city1);
        trip.getCities().add(city2);

        Reservation reservation = new Reservation(1L, "Joao", trip, city1, city2, 1, 10.0);

        ResponseEntity<Reservation> response = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/reservations", reservation, Reservation.class);

        Reservation createdReservation = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reservation.getName(), createdReservation.getName());
        assertEquals(reservation.getTrip().getId(), createdReservation.getTrip().getId());
        assertEquals(reservation.getInitialCity().getId(), createdReservation.getInitialCity().getId());
        assertEquals(reservation.getFinalCity().getId(), createdReservation.getFinalCity().getId());
    }

    // Parameterized test to cover different scenarios of invalid reservations
    static Stream<Reservation> invalidReservations() {
        return Stream.of(
                new Reservation(1L, "", new Trip(1L, LocalDateTime.now(), 10.0, 10), new City(1L, "Porto"), new City(2L, "Lisboa"), 1, 10.0),
                new Reservation(1L, "Joao", new Trip(1L, LocalDateTime.now(), 10.0, 100), new City(1L, "Porto"), new City(2L, "Lisboa"), 10000, 10.0),
                new Reservation(1L, "Joao", new Trip(1L, LocalDateTime.now(), 10.0, 10), new City(1L, "Porto"), new City(100L, "Madrid"), 1, 10.0),
                new Reservation(1L, "Joao", new Trip(1L, LocalDateTime.now(), 10.0, 10), new City(1L, "Porto"), new City(1L, "Porto"), 1, 10.0),
                new Reservation(1L, "Joao", new Trip(1L, LocalDateTime.now(), 10.0, 10), new City(2L, "Lisboa"), new City(1L, "Porto"), 1, 10.0),
                new Reservation(1L, "Joao", new Trip(1L, LocalDateTime.now(), 10.0, 10), new City(1L, "Porto"), new City(2L, "Lisboa"), 0, 10.0)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidReservations")
    void testCreateInvalidReservation(Reservation reservation) {
        ResponseEntity<Reservation> response = restTemplate.postForEntity("http://localhost:" + randomServerPort + "/reservations", reservation, Reservation.class);

        Reservation createdReservation = response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(createdReservation);
    }
}
