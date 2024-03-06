package tqs.lab3.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import ch.qos.logback.classic.Logger;
import jakarta.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import tqs.lab3.cars.Car;
import tqs.lab3.cars.CarsRepository;
import tqs.lab3.cars.CarService;


@ExtendWith(MockitoExtension.class)
public class CarRepositoryTest {

    @Mock(lenient = true)
    private CarsRepository carsRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        Car car = new Car("maker", "model");
        Car car2 = new Car("maker2", "model2");
        car.setId(1L);
        car2.setId(2L);
        List<Car> allCars = Arrays.asList(car, car2);
        Mockito.when(carsRepository.findAll()).thenReturn(allCars);
        Mockito.when(carsRepository.findById(1L)).thenReturn(java.util.Optional.of(car));
        Mockito.when(carsRepository.findById(2L)).thenReturn(java.util.Optional.of(car2));
        Mockito.when(carsRepository.findById(3L)).thenReturn(java.util.Optional.empty());
        //returns a list with the car with maker "maker"
        Mockito.when(carsRepository.findByMaker("maker")).thenReturn(Arrays.asList(car));
    }

    @Test
    public void givenCars_whenGetCars_thenReturnJsonArray() {
        List<Car> allCars = carService.getAllCars();
        assert(allCars.size() == 2);
        assert(allCars.get(0).getMaker().equals("maker"));
        assert(allCars.get(1).getMaker().equals("maker2"));
    }

    @Test
    public void givenCar_whenGetCarById_thenReturnCar() {
        Car car = carService.getCarById(1L);
        assert(car.getMaker().equals("maker"));
    }

    @Test
    public void givenInvalidCarId_whenGetCarById_thenReturnNull() {
        Car car = carService.getCarById(3L);
        assert(car == null);
    }

    @Test
    public void givenCarMaker_whenGetCarByMaker_thenReturnCar() {
        Car car = carService.getCarByMaker("maker").get(0);
        assert(car.getMaker().equals("maker"));
    }
    
}
