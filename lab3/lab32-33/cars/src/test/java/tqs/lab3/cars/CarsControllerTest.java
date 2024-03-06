package tqs.lab3.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import ch.qos.logback.classic.Logger;

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


@WebMvcTest(CarsController.class)
public class CarsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService carService;

    Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(CarsControllerTest.class);

    @BeforeEach
    public void setUp() {
        Car car = new Car("maker", "model");
        car.setId(1L);
        List<Car> allCars = Arrays.asList(car);
        Mockito.when(carService.getAllCars()).thenReturn(allCars);
        Mockito.when(carService.getCarById(1L)).thenReturn(car);
    }

    @Test
    public void givenCars_whenGetCars_thenReturnJsonArray() throws Exception {
        mvc.perform(get("/cars/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].maker", is("maker")));
    }

    @Test
    public void givenCar_whenGetCar_thenReturnJson() throws Exception {
        mvc.perform(get("/cars/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is("maker")));
    }

    @Test
    public void testPostCar() throws Exception {

        Car car = new Car("maker", "model");

        when(carService.saveCar(Mockito.any())).thenReturn(car);

        logger.info("AQUI");
        // logger.info(carService.saveCar(car).toString());

        mvc.perform(post("/cars/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(car)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.maker", is("maker")));
        

        
    }
    

    

}
