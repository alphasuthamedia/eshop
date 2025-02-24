package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Car;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class CarRepository {
    static int id = 0;
    private List<Car> carData = new ArrayList<>();
    public Car createCar(Car car) {
        if (car.getCarID() == null) {
            UUID uuid = UUID.randomUUID();
            car.setCarID(uuid.toString());
        }
        carData.add(car);
        return car;
    }
    public Iterator<Car> findAll() {
        return carData.iterator();
    }
    public Car findById(int id) {
        for (Car car : carData) {
            if (car.getCarID().equals(id)) {
                return car;
            }
        }
        return null;
    }
    public Car update(String id, Car updatedCar) {
        for (int i = 0; i < carData.size(); i++) {
            Car car = carData.get(i);
            if (car.getCarID().equals(id)) {
                car.setCarName(updatedCar.getCarName());
                car.setCarColor(updatedCar.getCarColor());
                car.setCarQuantity(updatedCar.getCarQuantity());
                return car;
            }
        }
        return null;
    }
    public void delete(String id) {
        carData.removeIf(car -> car.getCarID().equals(id));
    }
}
