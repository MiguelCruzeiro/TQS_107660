package tqs.lab3.cars;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import ch.qos.logback.classic.Logger;
import net.bytebuddy.asm.Advice.Local;

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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "application-integrationtest.properties")
public class APITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarsRepository carsRepository;

    @AfterEach
    public void resetDb() {
        carsRepository.deleteAll();
    }

    @Test
    public void whenValidCar_thenCreateCar() {
        Car car = new Car("Ferrari", "F8");
        ResponseEntity<Car> postCar = restTemplate.postForEntity("/cars/save", car, Car.class);

        Optional<Car> returnedCar = carsRepository.findById(postCar.getBody().getId());

        assertThat(postCar.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(returnedCar).isNotEmpty();
        assertThat(returnedCar.get().getMaker()).isEqualTo(car.getMaker());
    }

    @Test
    public void givenCars_whenGetCars_thenReturnJsonArray() {
        Car car = new Car("Ferrari", "F8");
        carsRepository.saveAndFlush(car);

        ResponseEntity<Car[]> response = restTemplate.getForEntity("/cars/all", Car[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getMaker()).isEqualTo(car.getMaker());
    }

    @Test
    public void givenCreate2Renaults_whenFindByMakerRenault_thenReturn2Renaults() {
        Car car1 = new Car("Porshe", "Taycan");
        Car car2 = new Car("Porshe", "Panamera");
        Car car3 = new Car("Opel", "Corsa");
        car1 = restTemplate.postForEntity("/cars/save", car1, Car.class).getBody();
        car2 = restTemplate.postForEntity("/cars/save", car2, Car.class).getBody();
        car3 = restTemplate.postForEntity("/cars/save", car3, Car.class).getBody();

        // assert that 3 cars are in database
        assertThat(carsRepository.findAll()).hasSize(3);
        //assert that 2 cars are Renault
        assertThat(carsRepository.findByMaker("Porshe")).hasSize(2);
    }
    
}
