package tqs.lab3.cars;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class Car {

    private String maker;
    private String model;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    public Car() {
    }

    public Car(String maker, String model, Long id) {
        this.maker = maker;
        this.model = model;
        this.id = id;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Car{" +
                "maker='" + maker + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
    
}
