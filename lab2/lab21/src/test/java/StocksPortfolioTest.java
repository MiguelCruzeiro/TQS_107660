import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.lab21.StocksPortfolio;
import tqs.lab21.IStockmarketService;
import tqs.lab21.Stock;


@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {

    @Mock
    IStockmarketService stockMarket;

    @InjectMocks
    StocksPortfolio stocksPortfolio;

    @Test
    void getTotalValueTest() {
        when(stockMarket.lookUpPrice("Apple")).thenReturn(100.0);
        when(stockMarket.lookUpPrice("Tesla")).thenReturn(200.0);
        //when(stockMarket.lookUpPrice("Microsoft")).thenReturn(200.0);


        stocksPortfolio.addStock(new Stock("Apple", 2));
        stocksPortfolio.addStock(new Stock("Tesla", 3));

        //stocksPortfolio.addStock(new Stock("Microsoft", 4));

        double result = stocksPortfolio.totalValue();
        assertTrue(result == 800.0);
        verify(stockMarket, times(2)).lookUpPrice(any());
    }

    



    
}
