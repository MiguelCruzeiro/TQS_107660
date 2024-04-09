package tqs.homework.hw1.Controller;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ch.qos.logback.classic.Logger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import tqs.homework.hw1.controllers.ReservationController;
import tqs.homework.hw1.models.City;
import tqs.homework.hw1.models.Reservation;
import tqs.homework.hw1.models.Trip;
import tqs.homework.hw1.services.CityService;
import tqs.homework.hw1.services.ReservationService;
import tqs.homework.hw1.services.TripService;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private TripService tripService;

    @MockBean
    private CityService cityService;

    Logger logger = (Logger) LoggerFactory.getLogger(ReservationController.class);

    Reservation reservation;

    @BeforeEach
    public void setUp() {
        Trip trip = new Trip(1L,LocalDateTime.now(), 10.0, 10);

        City city1 = new City(1L, "Porto");
        City city2 = new City(2L, "Lisboa");
        City city3 = new City(3L, "Aveiro");

        trip.getCities().add(city1);
        trip.getCities().add(city2);

        reservation = new Reservation(1L, "Joao", trip, city1, city2, 1, 10.0);

        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(reservation));
        when(reservationService.getReservationById(1L)).thenReturn(reservation);
        when(tripService.getTripById(1L)).thenReturn(trip);
        when(cityService.getCityByName("Porto")).thenReturn(city1);
        when(cityService.getCityByName("Lisboa")).thenReturn(city2);
        when(reservationService.getReservationById(2L)).thenReturn(null);
        when(reservationService.saveReservation(any())).thenReturn(new Reservation(1L, "Joao", new Trip(1L,LocalDateTime.now(), 10.0, 10), new City(1L, "Porto"), new City(2L, "Lisboa"), 1, 10.0));


    }

    @Test
    void testGetAllReservations() throws Exception {
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name").value("Joao"));
    }

    @Test
    void testGetReservationById() throws Exception {
        mockMvc.perform(get("/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Joao"));
    }

    @Test
    void testGetReservationByIdNotFound() throws Exception {
        mockMvc.perform(get("/reservations/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostReservation() throws Exception {

        mockMvc.perform(post("/reservations")
                .contentType("application/json")
                .content("{\"name\": \"Joao\", \"trip\": {\"id\": 1}, \"initialCity\": {\"name\": \"Porto\"}, \"finalCity\": {\"name\": \"Lisboa\"}, \"numSeats\": 1, \"totalPrice\": 10.0}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Joao"));
    }

    @Test
    void testPostReservationBadRequest() throws Exception {

        mockMvc.perform(post("/reservations")
                .contentType("application/json")
                .content("{\"name\": \"Joao\", \"trip\": {\"id\": 1}, \"initialCity\": {\"name\": \"Porto\"}, \"finalCity\": {\"name\": \"Porto\"}, \"numSeats\": 1, \"totalPrice\": 10.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostReservationBadRequest2() throws Exception {

        mockMvc.perform(post("/reservations")
                .contentType("application/json")
                .content("{\"name\": \"Joao\", \"trip\": {\"id\": 1}, \"initialCity\": {\"name\": \"Porto\"}, \"finalCity\": {\"name\": \"Lisboa\"}, \"numSeats\": 0, \"totalPrice\": 10.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostReservationBadRequest3() throws Exception {

        mockMvc.perform(post("/reservations")
                .contentType("application/json")
                .content("{\"name\": \"\", \"trip\": {\"id\": 1}, \"initialCity\": {\"name\": \"Porto\"}, \"finalCity\": {\"name\": \"Lisboa\"}, \"numSeats\": 1, \"totalPrice\": 10.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostReservationNotFound() throws Exception {

        mockMvc.perform(post("/reservations")
                .contentType("application/json")
                .content("{\"name\": \"Joao\", \"trip\": {\"id\": 2}, \"initialCity\": {\"name\": \"Porto\"}, \"finalCity\": {\"name\": \"Lisboa\"}, \"numSeats\": 1, \"totalPrice\": 10.0}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostReservationNotEnoughSeats() throws Exception {

        mockMvc.perform(post("/reservations")
                .contentType("application/json")
                .content("{\"name\": \"Joao\", \"trip\": {\"id\": 1}, \"initialCity\": {\"name\": \"Porto\"}, \"finalCity\": {\"name\": \"Lisboa\"}, \"numSeats\": 11, \"totalPrice\": 10.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostReservationInitialCityNotInTrip() throws Exception {

        mockMvc.perform(post("/reservations")
                .contentType("application/json")
                .content("{\"name\": \"Joao\", \"trip\": {\"id\": 1}, \"initialCity\": {\"name\": \"Lisboa\"}, \"finalCity\": {\"name\": \"Lisboa\"}, \"numSeats\": 1, \"totalPrice\": 10.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostReservationFinalCityNotInTrip() throws Exception {

        mockMvc.perform(post("/reservations")
                .contentType("application/json")
                .content("{\"name\": \"Joao\", \"trip\": {\"id\": 1}, \"initialCity\": {\"name\": \"Porto\"}, \"finalCity\": {\"name\": \"Aveiro\"}, \"numSeats\": 1, \"totalPrice\": 10.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostReservationInitialCityAfterFinalCity() throws Exception {

        mockMvc.perform(post("/reservations")
                .contentType("application/json")
                .content("{\"name\": \"Joao\", \"trip\": {\"id\": 1}, \"initialCity\": {\"name\": \"Lisboa\"}, \"finalCity\": {\"name\": \"Porto\"}, \"numSeats\": 1, \"totalPrice\": 10.0}"))
                .andExpect(status().isBadRequest());
    }
    
    
}
