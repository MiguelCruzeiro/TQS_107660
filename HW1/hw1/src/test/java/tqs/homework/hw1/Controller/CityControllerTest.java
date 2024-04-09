package tqs.homework.hw1.Controller;

import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ch.qos.logback.classic.Logger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import tqs.homework.hw1.controllers.CityController;
import tqs.homework.hw1.models.City;
import tqs.homework.hw1.services.CityService;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    Logger logger = (Logger) LoggerFactory.getLogger(CityController.class);

    City city;

    @BeforeEach
    public void setUp() {
        City city = new City(1L, "Porto");

        when(cityService.getAllCities()).thenReturn(Arrays.asList(city));
        when(cityService.getCityById(1L)).thenReturn(city);
        when(cityService.getCityByName("Porto")).thenReturn(city);
        when(cityService.getCityByName("Lisboa")).thenReturn(null);
        
    }

    @Test
    void testGetAllCities() throws Exception {
        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name").value("Porto"));
    }

    @Test
    void testGetCityById() throws Exception {
        mockMvc.perform(get("/cities/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Porto"));
    }

    @Test
    void testGetCityByNameNotFound() throws Exception {
        mockMvc.perform(get("/cities/name/Lisboa"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostCity() throws Exception {
        when(cityService.saveCity(any())).thenReturn(new City(1L, "Aveiro"));

        mockMvc.perform(post("/cities")
                .contentType("application/json")
                .content("{\"name\": \"Aveiro\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Aveiro"));

        verify(cityService, times(1)).saveCity(any());
    }

    @Test
    void testGetCityByIdNotFound() throws Exception {
        mockMvc.perform(get("/cities/20"))
                .andExpect(status().isNotFound());
    }

    
}
