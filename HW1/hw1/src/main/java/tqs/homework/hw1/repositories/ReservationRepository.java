package tqs.homework.hw1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.homework.hw1.models.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

    
}
