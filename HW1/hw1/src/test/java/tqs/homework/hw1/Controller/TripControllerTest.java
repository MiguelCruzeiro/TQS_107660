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

import tqs.homework.hw1.controllers.TripController;
import tqs.homework.hw1.models.City;
import tqs.homework.hw1.models.Trip;
import tqs.homework.hw1.services.CityService;
import tqs.homework.hw1.services.TripService;

@WebMvcTest(TripController.class)
class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    @MockBean
    private CityService cityService;

    Logger logger = (Logger) LoggerFactory.getLogger(TripController.class);

    Trip trip;

    @BeforeEach
    public void setUp() {
        Trip trip = new Trip(1L,LocalDateTime.now(), 10.0, 10);

        City city1 = new City(1L, "Porto");
        City city2 = new City(2L, "Lisboa");

        trip.getCities().add(city1);
        trip.getCities().add(city2);

        when(tripService.getAllTrips()).thenReturn(Arrays.asList(trip));
        when(tripService.getTripById(1L)).thenReturn(trip);
        when(cityService.getCityByName("Porto")).thenReturn(city1);
        when(cityService.getCityByName("Lisboa")).thenReturn(city2);
        when(tripService.getTripsByCities(city1, city2)).thenReturn(Arrays.asList(trip));
        when(tripService.getTripById(2L)).thenReturn(null);
        when(tripService.getPriceBetweenCities(trip, city1, city2, "EUR")).thenReturn(10.0);
    
    }

    @Test
    void whenGetAllTrips_thenReturnTrips() throws Exception {
        mockMvc.perform(get("/trips/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].basePrice").value(10.0))
                .andExpect(jsonPath("$[0].cities[0].name").value("Porto"))
                .andExpect(jsonPath("$[0].cities[1].name").value("Lisboa"));

        verify(tripService, times(1)).getAllTrips();
    }

    @Test
    void whenGetTripById_thenReturnTrip() throws Exception {

        mockMvc.perform(get("/trips/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.basePrice").value(10.0))
                .andExpect(jsonPath("$.cities[0].name").value("Porto"))
                .andExpect(jsonPath("$.cities[1].name").value("Lisboa"));

        verify(tripService, times(1)).getTripById(1L);
    }

    @Test
    void whenGetTripById_thenReturnNotFound() throws Exception {

        mockMvc.perform(get("/trips/2"))
                .andExpect(status().isNotFound());

        verify(tripService, times(1)).getTripById(2L);
    }

    @Test
    void whenGetTripByCities_thenReturnTrips() throws Exception {

        mockMvc.perform(get("/trips/trips?city1=Porto&city2=Lisboa"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].basePrice").value(10.0))
                .andExpect(jsonPath("$[0].cities[0].name").value("Porto"))
                .andExpect(jsonPath("$[0].cities[1].name").value("Lisboa"));

        verify(cityService, times(2)).getCityByName(anyString());
        verify(tripService, times(1)).getTripsByCities(any(City.class), any(City.class));
    }

    @Test
    void whenGetTripByCities_thenReturnNotFound() throws Exception {

        when(cityService.getCityByName("Madrid")).thenReturn(null);

        mockMvc.perform(get("/trips/trips?city1=Porto&city2=Madrid"))
                .andExpect(status().isNotFound());

        verify(cityService, times(2)).getCityByName(anyString());
        verify(tripService, times(0)).getTripsByCities(any(City.class), any(City.class));
    }

    @Test
    void whenGetTripPrice_thenReturnNotFound() throws Exception {

        mockMvc.perform(get("/trips/2/price/Porto/Lisboa/5/EUR"))
                .andExpect(status().isNotFound());

        verify(cityService, times(2)).getCityByName(anyString());
        verify(tripService, times(1)).getTripById(2L);
    }

    @Test
    void whenPostTrip_thenReturnsCreated() throws Exception {

        when(tripService.saveTrip(any(Trip.class))).thenReturn(new Trip(1L,LocalDateTime.now(), 10.0, 10));

        mockMvc.perform(post("/trips")
                .contentType("application/json")
                .content("{\"basePrice\": 10.0, \"numSeats\": 10, \"cities\": [{\"name\": \"Porto\"}, {\"name\": \"Lisboa\"}]}"))
                .andExpect(status().isCreated());

        verify(tripService, times(1)).saveTrip(any(Trip.class));
    }

    @Test
    void whenGetAllTripsbyCities_thenReturnBadRequest() throws Exception {

        mockMvc.perform(get("/trips/trips?city1=Porto&city2="))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/trips/trips?city1=Porto&city2="))
                .andExpect(status().isBadRequest());

        verify(tripService, times(0)).getTripsByCities(any(City.class), any(City.class));
    }

    @Test
    void whenGetTripsPrice_thenReturnNotFound() throws Exception {

        when(cityService.getCityByName("Madrid")).thenReturn(null);

        mockMvc.perform(get("/trips/2/price/Porto/Madrid/5/EUR"))
                .andExpect(status().isNotFound());

        verify(tripService, times(0)).getTripById(2L);
    }

    @Test
    void whenGetAllTripsPrice_thenReturnBadRequest() throws Exception {

        mockMvc.perform(get("/trips/1/price/Porto/Lisboa/0/EUR"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/trips/1/price/Porto/Lisboa/1/123"))
                .andExpect(status().isBadRequest());

        verify(cityService, times(4)).getCityByName(anyString());
        verify(tripService, times(0)).getTripById(1L);
    }

    @Test
    void whenGetTripPrice_thenReturnPrice() throws Exception {

        mockMvc.perform(get("/trips/1/price/Porto/Lisboa/5/EUR"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(50.0));

        verify(cityService, times(2)).getCityByName(anyString());
        verify(tripService, times(1)).getTripById(1L);
    }


    
    
}
