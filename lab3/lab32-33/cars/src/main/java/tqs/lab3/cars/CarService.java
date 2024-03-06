package tqs.lab3.cars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CarService {

    @Autowired
    private CarsRepository carsRepository;

    public Car saveCar(Car car) {
        Car s = carsRepository.save(car);
        return s;
    }

    public Car getCarById(Long id) {
        if (carsRepository.findById(id).isPresent()) {
            return carsRepository.findById(id).get();
        }
        return null;
    }

    public List<Car> getCarByMaker(String maker) {
        return carsRepository.findByMaker(maker);
    }

    public Car getCarByModel(String model) {
        return carsRepository.findByModel(model);
    }

    public List<Car> getAllCars() {
        return carsRepository.findAll();
    }
    
}
