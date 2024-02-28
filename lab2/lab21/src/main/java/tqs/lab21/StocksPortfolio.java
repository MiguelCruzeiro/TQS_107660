package tqs.lab21;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private IStockmarketService stockMarket;
    List<Stock> stocks = new ArrayList<Stock>();

    public StocksPortfolio(IStockmarketService stockMarket) {
        this.stockMarket = stockMarket;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double totalValue() {
        double total = 0;
        for (Stock stock : stocks) {
            if (stock != null) {
                total += stockMarket.lookUpPrice(stock.getName()) * stock.getValue();
            }
        }
        return total;
    }
    
}
