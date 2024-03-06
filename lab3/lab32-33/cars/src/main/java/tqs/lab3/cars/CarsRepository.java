package tqs.lab3.cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import tqs.lab3.cars.Car;

@Repository
public interface CarsRepository extends JpaRepository<Car, Long>{

    public List<Car> findByMaker(String maker);
    public Car findByModel(String model);
    public Car save(Car car);

    
}
