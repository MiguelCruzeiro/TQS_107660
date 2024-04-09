package tqs.homework.hw1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.homework.hw1.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

    City findByName(String name);
    
}
