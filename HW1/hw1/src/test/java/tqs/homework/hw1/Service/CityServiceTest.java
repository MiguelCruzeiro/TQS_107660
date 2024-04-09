package tqs.homework.hw1.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import tqs.homework.hw1.repositories.CityRepository;
import tqs.homework.hw1.services.CityService;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    private City city1;
    private City city2;

    @BeforeEach
    public void setUp() {
        city1 = new City(1L, "Porto");
        city2 = new City(2L, "Lisboa");
    }

    @Test
    void whenValidName_thenCityShouldBeFound() {
        when(cityRepository.findByName(city1.getName())).thenReturn(city1);

        City found = cityService.getCityByName(city1.getName());

        assertEquals(city1.getName(), found.getName());
    }

    @Test
    void whenInvalidName_thenCityShouldNotBeFound() {
        when(cityRepository.findByName("doesNotExist")).thenReturn(null);

        City found = cityService.getCityByName("doesNotExist");

        assertEquals(null, found);
    }

    @Test
    void whenValidId_thenCityShouldBeFound() {
        when(cityRepository.findById(city1.getId())).thenReturn(Optional.of(city1));

        Optional<City> found = Optional.ofNullable(cityService.getCityById(city1.getId()));

        assertEquals(city1.getName(), found.get().getName());
    }

    @Test
    void whenInvalidId_thenCityShouldNotBeFound() {
        when(cityRepository.findById(-11L)).thenReturn(Optional.empty());

        Optional<City> found = Optional.ofNullable(cityService.getCityById(-11L));

        assertEquals(Optional.empty(), found);
    }

    @Test
    void whenFindAll_thenReturnAllCities() {
        List<City> cities = new ArrayList<>(Arrays.asList(city1, city2));

        when(cityRepository.findAll()).thenReturn(cities);

        List<City> found = cityService.getAllCities();

        assertEquals(2, found.size());
    }

    @Test
    void whenSave_thenSaveCity() {
        when(cityRepository.save(any(City.class))).thenReturn(city1);

        City saved = cityService.saveCity(city1);

        assertEquals(city1.getName(), saved.getName());
    }

    @Test
    void whenDelete_thenDeleteCity() {
        cityService.deleteCity(city1);

        verify(cityRepository, times(1)).delete(city1);
    }

    @Test
    void whenDeleteById_thenDeleteCity() {
        cityService.deleteCityById(city1.getId());

        verify(cityRepository, times(1)).deleteById(city1.getId());
    }

    
}
