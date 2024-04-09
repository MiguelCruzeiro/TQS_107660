package tqs.homework.hw1.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.homework.hw1.models.City;
import tqs.homework.hw1.models.Trip;
import tqs.homework.hw1.repositories.TripRepository;
import tqs.homework.hw1.services.CurrencyExchangeService;
import tqs.homework.hw1.services.TripService;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;


    @Mock
    private CurrencyExchangeService currencyExchangeService;


    private Trip trip1;
    private Trip trip2;
    private Trip trip3;

    private City city1;
    private City city2;
    private City city3;
    private City city4;

    @BeforeEach
    public void setUp() {
        trip1 = new Trip(1L, LocalDateTime.now(), 10.0, 10);
        trip2 = new Trip(2L, LocalDateTime.now(), 10.0, 10);
        trip3 = new Trip(3L, LocalDateTime.now(), 10.0, 10);

        city1 = new City(1L, "Porto");
        city2 = new City(2L, "Lisboa");
        city3 = new City(3L, "Braga");
        city4 = new City(4L, "Aveiro");

        trip1.getCities().add(city1);
        trip1.getCities().add(city2);

        trip2.getCities().add(city3);
        trip2.getCities().add(city4);

        trip3.getCities().add(city1);
        trip3.getCities().add(city2);
        trip3.getCities().add(city3);
        trip3.getCities().add(city4);

        

    }

    @Test
    void whenValidId_thenTripShouldBeFound() {
        when(tripRepository.findById(trip1.getId())).thenReturn(Optional.of(trip1));

        Optional<Trip> found = Optional.ofNullable(tripService.getTripById(trip1.getId()));

        assertTrue(found.isPresent());
        assertEquals(trip1.getId(), found.get().getId());

        verify(tripRepository, times(1)).findById(trip1.getId());

    }

    @Test
    void whenInvalidId_thenTripShouldNotBeFound() {
        when(tripRepository.findById(-11L)).thenReturn(Optional.empty());

        Optional<Trip> found = Optional.ofNullable(tripService.getTripById(-11L));

        assertTrue(found.isEmpty());

        verify(tripRepository, times(1)).findById(-11L);
    }

    @Test
    void whenFindAll_thenReturnAllTrips() {
        when(tripRepository.findAll()).thenReturn(Arrays.asList(trip1, trip2));

        List<Trip> allTrips = tripService.getAllTrips();

        assertEquals(2, allTrips.size());

        verify(tripRepository, times(1)).findAll();
    }

    @Test
    void whenSave_thenSaveTrip() {
        when(tripRepository.save(any(Trip.class))).thenReturn(trip1);

        Trip savedTrip = tripService.saveTrip(trip1);

        assertEquals(trip1.getId(), savedTrip.getId());

        verify(tripRepository, times(1)).save(trip1);
    }

    @Test
    void whenDelete_thenDeleteTrip() {
        tripService.deleteTrip(trip1);

        verify(tripRepository, times(1)).delete(trip1);
    }

    @Test
    void whenGetTripsByCities_thenReturnTrips() {
        when(tripRepository.findByCities(city1)).thenReturn(Arrays.asList(trip1));
        when(tripRepository.findByCities(city2)).thenReturn(Arrays.asList(trip1));
        when(tripRepository.findByCities(city3)).thenReturn(Arrays.asList(trip2));
        when(tripRepository.findByCities(city4)).thenReturn(Arrays.asList(trip2));

        List<Trip> trips = tripService.getTripsByCities(city1, city2);

        assertEquals(1, trips.size());
        assertEquals(trip1.getId(), trips.get(0).getId());

        trips = tripService.getTripsByCities(city3, city4);

        assertEquals(1, trips.size());
        assertEquals(trip2.getId(), trips.get(0).getId());

        verify(tripRepository, times(4)).findByCities(any(City.class));
    }

    @Test
    void whenGetIntermediateCities_thenReturnCities() {
        List<City> cities = new ArrayList<>(Arrays.asList(city1, city2, city3, city4));

        List<City> intermediateCities = tripService.getIntermediateCities(city1, city4, trip3);

        assertEquals(4, intermediateCities.size());
        assertEquals(cities, intermediateCities);
    }

    @Test
    void whenGetPriceBetweenCities_thenReturnPrice() {

        when (currencyExchangeService.getExchangeRate("EUR")).thenReturn(1.0);

        Double price = tripService.getPriceBetweenCities(trip1, city1, city2, "EUR");

        assertEquals(10.0, price);

    }



}
