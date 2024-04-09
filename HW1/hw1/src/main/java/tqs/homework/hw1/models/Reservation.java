package tqs.homework.hw1.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JsonIgnoreProperties({"reservations", "cities"})
    private Trip trip;

    
    @ManyToOne
    @JsonIgnoreProperties({"reservations", "cities", "trips"})
    private City initialCity;

    @ManyToOne
    @JsonIgnoreProperties({"reservations", "cities", "trips"})
    private City finalCity;

    @Column(nullable = false)
    private int numSeats;

    @Column(nullable = false)
    private double totalPrice;

    //get the total price of the reservation
    

    public Reservation(String name, Trip trip, int numSeats, City initialCity, City finalCity, double totalPrice) {
        this.name = name;
        this.trip = trip;
        this.numSeats = numSeats;
        this.initialCity = initialCity;
        this.finalCity = finalCity;
        this.totalPrice = totalPrice;
    }

}
