package tqs.homework.hw1.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trip")
public class Trip implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime departureDateTime;

    @Column(nullable = false)
    private Double basePrice;

    @Column(nullable = false)
    private int seats;

    //list of cities that the trip goes through
    @ManyToMany
    @JsonIgnoreProperties({"trips"})
    private List<City> cities = new ArrayList<>();

    //list of reservations for this trip
    @OneToMany(mappedBy = "trip")
    @JsonIgnoreProperties({"trip" , "cities"})
    private List<Reservation> reservations = new ArrayList<>();

    public int getAvailableSeats() {
        return this.seats - this.reservations.stream().mapToInt(Reservation::getNumSeats).sum();
    }

    public Trip(LocalDateTime departureDateTime, Double basePrice, int seats) {
        this.departureDateTime = departureDateTime;
        this.basePrice = basePrice;
        this.seats = seats;
    }

    public Trip(Long id, LocalDateTime departureDateTime, Double basePrice, int seats) {
        this.id = id;
        this.departureDateTime = departureDateTime;
        this.basePrice = basePrice;
        this.seats = seats;
    }

    
    

    
}
