package tqs.homework.hw1.Controller;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ch.qos.logback.classic.Logger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import tqs.homework.hw1.controllers.CurrencyExchangeController;
import tqs.homework.hw1.services.CurrencyExchangeService;

@WebMvcTest(CurrencyExchangeController.class)
class CurrencyExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyExchangeService currencyExchangeService;

    Logger logger = (Logger) LoggerFactory.getLogger(CurrencyExchangeController.class);

    @BeforeEach
    public void setUp() {
        when(currencyExchangeService.getAllCurrencyExchanges()).thenReturn(new HashMap<>());
        when(currencyExchangeService.getExchangeRate("USD")).thenReturn(1.0);
        when(currencyExchangeService.getAllCurrencies()).thenReturn(Arrays.asList("USD", "EUR"));
    }

    @Test
    void testGetCurrencyExchange() throws Exception {
        mockMvc.perform(get("/currency/USD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(1.0));
    }

    @Test
    void testGetAllCurrencies() throws Exception {
        mockMvc.perform(get("/currency/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0]").value("USD"))
                .andExpect(jsonPath("$[1]").value("EUR"));
    }

    @Test
    void testGetAllCurrencyExchanges() throws Exception {
        mockMvc.perform(get("/currency/rates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isEmpty()); // Check if the response body is empty
    }
    
}
