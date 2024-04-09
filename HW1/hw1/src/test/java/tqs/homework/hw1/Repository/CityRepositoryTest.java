package tqs.homework.hw1.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.homework.hw1.models.City;
import tqs.homework.hw1.repositories.CityRepository;

@DataJpaTest
class CityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    @Test
    void whenFindByName_thenReturnCity() {
        City city = new City("Porto");
        entityManager.persistAndFlush(city);

        City found = cityRepository.findByName(city.getName());
        assertEquals(city.getName(), found.getName());
    }

    @Test
    void whenInvalidName_thenReturnNull() {
        City fromDb = cityRepository.findByName("doesNotExist");
        assertEquals(null, fromDb);
    }

    @Test
    void whenFindById_thenReturnCity() {
        City city = new City("Porto");
        entityManager.persistAndFlush(city);

        Optional<City> found = cityRepository.findById(city.getId());
        assertEquals(city.getName(), found.get().getName());
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Optional<City> fromDb = cityRepository.findById(-11L);
        assertEquals(Optional.empty(), fromDb);
    }

    @Test
    void whenFindAll_thenReturnAllCities() {
        City city1 = new City("Porto");
        City city2 = new City("Lisboa");
        entityManager.persistAndFlush(city1);
        entityManager.persistAndFlush(city2);

        Iterable<City> allCities = cityRepository.findAll();
        assertEquals(2, ((java.util.Collection<?>) allCities).size());
    }
    
}
