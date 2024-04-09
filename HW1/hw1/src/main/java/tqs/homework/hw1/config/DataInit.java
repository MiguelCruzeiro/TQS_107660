package tqs.homework.hw1.config;

import tqs.homework.hw1.models.*;
import tqs.homework.hw1.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInit {

    private final CityRepository cityRepository;
	private final TripRepository tripRepository;
	private final ReservationRepository reservationRepository;

	@Autowired
	public DataInit(CityRepository cityRepository, TripRepository tripRepository, ReservationRepository reservationRepository) {
		this.cityRepository = cityRepository;
		this.tripRepository = tripRepository;
		this.reservationRepository = reservationRepository;
	}

	@PostConstruct
	public void initialize() throws Exception {
		

		City city1 = new City("Porto");
		City city2 = new City("Lisboa");
		City city3 = new City("Braga");
		City city4 = new City("Aveiro");
		City city5 = new City("Coimbra");
		City city6 = new City("Faro");
		City city7 = new City("Viseu");
		City city8 = new City("Viana do Castelo");
		City city9 = new City("Guarda");
		City city10 = new City("Leiria");
		City city11 = new City("Setubal");
		City city12 = new City("Evora");
		City city13 = new City("Beja");
		City city14 = new City("Castelo Branco");
		City city15 = new City("Santarem");
		City city16 = new City("Portalegre");
		City city17 = new City("Braganca");
		City city18 = new City("Vila Real");

		cityRepository.save(city1);
		cityRepository.save(city2);
		cityRepository.save(city3);
		cityRepository.save(city4);
		cityRepository.save(city5);
		cityRepository.save(city6);
		cityRepository.save(city7);
		cityRepository.save(city8);
		cityRepository.save(city9);
		cityRepository.save(city10);
		cityRepository.save(city11);
		cityRepository.save(city12);
		cityRepository.save(city13);
		cityRepository.save(city14);
		cityRepository.save(city15);
		cityRepository.save(city16);
		cityRepository.save(city17);
		cityRepository.save(city18);

		Trip trip1 = new Trip(LocalDateTime.now(), 10.0, 100);
		Trip trip2 = new Trip(LocalDateTime.now(), 20.0, 200);
		Trip trip3 = new Trip(LocalDateTime.now(), 30.0, 300);
		Trip trip4 = new Trip(LocalDateTime.now(), 40.0, 400);
		Trip trip5 = new Trip(LocalDateTime.now(), 50.0, 500);

		trip1.getCities().add(city1);
		trip1.getCities().add(city2);
		trip1.getCities().add(city3);
		trip1.getCities().add(city4);
		trip1.getCities().add(city5);
		trip1.getCities().add(city6);
		trip1.getCities().add(city7);
		trip1.getCities().add(city8);

		trip2.getCities().add(city1);
		trip2.getCities().add(city2);
		trip2.getCities().add(city3);
		trip2.getCities().add(city4);

		trip3.getCities().add(city1);
		trip3.getCities().add(city2);
		trip3.getCities().add(city3);
		trip3.getCities().add(city4);

		trip4.getCities().add(city1);
		trip4.getCities().add(city2);
		trip4.getCities().add(city3);


		trip5.getCities().add(city1);
		trip5.getCities().add(city2);

		tripRepository.save(trip1);
		tripRepository.save(trip2);
		tripRepository.save(trip3);
		tripRepository.save(trip4);
		tripRepository.save(trip5);

		Reservation reservation1 = new Reservation("Joao", trip1, 2, city1, city8, 10.0);
		Reservation reservation2 = new Reservation("Maria", trip2, 3, city1, city4, 20.0);
		Reservation reservation3 = new Reservation("Pedro", trip3, 4, city1, city4, 30.0);
		Reservation reservation4 = new Reservation("Ana", trip4, 5, city1, city3, 40.0);
		Reservation reservation5 = new Reservation("Rita", trip5, 6, city1, city2, 50.0);

		reservationRepository.save(reservation1);
		reservationRepository.save(reservation2);
		reservationRepository.save(reservation3);
		reservationRepository.save(reservation4);
		reservationRepository.save(reservation5);


	}
    
}
