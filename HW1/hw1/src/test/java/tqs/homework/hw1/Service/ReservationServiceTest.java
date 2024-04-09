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
import tqs.homework.hw1.models.Reservation;
import tqs.homework.hw1.models.Trip;
import tqs.homework.hw1.repositories.ReservationRepository;
import tqs.homework.hw1.services.ReservationService;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation1;
    private Reservation reservation2;

    private Trip trip1;
    private Trip trip2;

    private City city1;
    private City city2;
    private City city3;
    private City city4;

    @BeforeEach
    public void setUp() {
        trip1 = new Trip(1L, LocalDateTime.now(), 10.0, 10);
        trip2 = new Trip(2L, LocalDateTime.now(), 10.0, 10);

        city1 = new City(1L, "Porto");
        city2 = new City(2L, "Lisboa");
        city3 = new City(3L, "Braga");
        city4 = new City(4L, "Aveiro");

        trip1.getCities().add(city1);
        trip1.getCities().add(city2);

        trip2.getCities().add(city3);
        trip2.getCities().add(city4);

        reservation1 = new Reservation("Joao", trip1, 1 ,city1, city2, 10.0);

        reservation2 = new Reservation("Maria", trip2, 2, city3, city4, 10.0);

    }

    @Test
    void whenValidId_thenReservationShouldBeFound() {
        when(reservationRepository.findById(reservation1.getId())).thenReturn(Optional.of(reservation1));

        Optional<Reservation> found = Optional.ofNullable(reservationService.getReservationById(reservation1.getId()));

        assertTrue(found.isPresent());
        assertEquals(reservation1.getId(), found.get().getId());

        verify(reservationRepository, times(1)).findById(reservation1.getId());

    }

    @Test
    void whenInvalidId_thenReservationShouldNotBeFound() {
        when(reservationRepository.findById(-11L)).thenReturn(Optional.empty());

        Optional<Reservation> found = Optional.ofNullable(reservationService.getReservationById(-11L));

        assertTrue(found.isEmpty());

        verify(reservationRepository, times(1)).findById(-11L);
    }

    @Test
    void whenFindAll_thenReturnAllReservations() {
        List<Reservation> reservations = new ArrayList<>(Arrays.asList(reservation1, reservation2));

        when(reservationRepository.findAll()).thenReturn(reservations);

        List<Reservation> found = reservationService.getAllReservations();

        assertEquals(2, found.size());

        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void whenSave_thenSaveReservation() {
        when(reservationRepository.save(any())).thenReturn(reservation1);

        Reservation saved = reservationService.saveReservation(reservation1);

        assertEquals(reservation1.getId(), saved.getId());

        verify(reservationRepository, times(1)).save(reservation1);
    }

    @Test
    void whenDelete_thenDeleteReservation() {
        reservationService.deleteReservation(reservation1);

        verify(reservationRepository, times(1)).delete(reservation1);
    }
    
}
