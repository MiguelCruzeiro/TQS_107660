package tqs.homework.hw1.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.homework.hw1.models.City;
import tqs.homework.hw1.models.Trip;
import tqs.homework.hw1.repositories.TripRepository;


@Service
public class TripService {

    private final CurrencyExchangeService currencyExchangeService;

    private final TripRepository tripRepository;


    @Autowired
    public TripService(TripRepository tripRepository, CurrencyExchangeService currencyExchangeService) {
        this.tripRepository = tripRepository;
        this.currencyExchangeService = currencyExchangeService;
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public void deleteTrip(Trip trip) {
        tripRepository.delete(trip);
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public List<Trip> getTripsByCities(City city1, City city2) {
         List<Trip> trips = tripRepository.findByCities(city1);
            trips.retainAll(tripRepository.findByCities(city2));
        
            //check if the city 1 is before city 2
            for (Trip trip : trips) {
                if (trip.getCities().indexOf(city1) > trip.getCities().indexOf(city2)) {
                    trips.remove(trip);
                }
            }
            return trips;
    }

    public Double getPriceBetweenCities(Trip trip, City initialCity, City finalCity, String currency) {
        Double exchangeRate = currencyExchangeService.getExchangeRate(currency);
        List<City> intermediateCities = getIntermediateCities(initialCity, finalCity, trip);
        Double price = trip.getBasePrice() * (intermediateCities.size()-1);
        if (exchangeRate != null) {
            price *= exchangeRate;
        }
        return price;
    }

    //get the cities between the initial and final city
    public List<City> getIntermediateCities(City initialCity, City finalCity, Trip trip) {
        List<City> intermediateCities = new ArrayList<>();
        boolean startAdding = false;
        for (City city : trip.getCities()) {
            if (city.equals(initialCity)) {
                startAdding = true;
            }
            if (startAdding) {
                intermediateCities.add(city);
            }
            if (city.equals(finalCity)) {
                break;
            }
        }
        return intermediateCities;
    }

    
    
}
