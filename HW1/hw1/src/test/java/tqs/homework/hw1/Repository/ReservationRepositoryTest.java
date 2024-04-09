package tqs.homework.hw1.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.homework.hw1.models.Reservation;
import tqs.homework.hw1.repositories.ReservationRepository;

@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void whenFindById_thenReturnReservation() {
        Reservation reservation = new Reservation();
        entityManager.persistAndFlush(reservation);

        Optional<Reservation> found = reservationRepository.findById(reservation.getId());
        assertEquals(reservation.getId(), found.get().getId());

    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Optional<Reservation> fromDb = reservationRepository.findById(-11L);
        assertEquals(Optional.empty(), fromDb);
    }

    @Test
    void whenFindAll_thenReturnAllReservations() {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        entityManager.persistAndFlush(reservation1);
        entityManager.persistAndFlush(reservation2);

        assertEquals(2, reservationRepository.findAll().size());
    }

    @Test
    void whenSave_thenSaveReservation() {
        Reservation reservation = new Reservation();
        entityManager.persistAndFlush(reservation);

        assertEquals(1, reservationRepository.findAll().size());
    }

}
