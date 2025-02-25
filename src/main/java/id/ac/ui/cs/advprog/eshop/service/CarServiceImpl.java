package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarServiceImpl implements id.ac.ui.cs.advprog.eshop.service.Service<Car> {
    @Autowired
    private CarRepository carRepository;

    @Override
    public Car create(Car car) {
        carRepository.create(car);
        return car;
    }
    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = carRepository.findAll();
        List<Car> allCars = new ArrayList<>();
        carIterator.forEachRemaining(allCars::add);
        return allCars;
    }
    @Override
    public Car getItemByID(String carID) {
        Car car = carRepository.findByID(carID);
        return car;
    }

    @Override
    public void editItem(Car car) {
        carRepository.update(car);
    }

    @Override
    public String removeByID(String carID) {
        carRepository.delete(carID);
        return "";
    }
}
