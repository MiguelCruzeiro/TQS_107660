package tqs.homework.hw1.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.homework.hw1.models.Trip;
import tqs.homework.hw1.repositories.TripRepository;

@DataJpaTest
class TripRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TripRepository tripRepository;

    @Test
    void whenFindById_thenReturnTrip() {
        Trip trip = new Trip(LocalDateTime.now(), 10.0, 10);
        entityManager.persistAndFlush(trip);

        Optional<Trip> found = tripRepository.findById(trip.getId());
        assertEquals(trip.getId(), found.get().getId());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Optional<Trip> fromDb = tripRepository.findById(-11L);
        assertEquals(Optional.empty(), fromDb);
    }

    @Test
    void whenFindAll_thenReturnAllTrips() {
        Trip trip1 = new Trip(LocalDateTime.now(), 10.0, 10);
        Trip trip2 = new Trip(LocalDateTime.now(), 10.0, 10);
        entityManager.persistAndFlush(trip1);
        entityManager.persistAndFlush(trip2);

        assertEquals(2, tripRepository.findAll().size());
    }

    @Test
    void whenSave_thenSaveTrip() {
        Trip trip = new Trip(LocalDateTime.now(), 10.0, 10);
        entityManager.persistAndFlush(trip);

        assertEquals(1, tripRepository.findAll().size());
    }
}
