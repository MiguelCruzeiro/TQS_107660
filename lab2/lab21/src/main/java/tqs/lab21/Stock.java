package tqs.lab21;

public class Stock {
    private String label;
    private double quantity;

    public Stock(String label, double quantity) {
        this.label = label;
        this.quantity = quantity;
    }

    public String getName() {
        return label;
    }

    public double getValue() {
        return quantity;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
}
