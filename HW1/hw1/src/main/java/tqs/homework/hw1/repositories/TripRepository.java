package tqs.homework.hw1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.homework.hw1.models.City;
import tqs.homework.hw1.models.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>{

    List<Trip> findByCities(City city1);


    
}
