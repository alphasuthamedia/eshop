package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;
    @Override
    public Car create(Car car) {
        // todo auto generated method sub
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
    public Car findByID(String carID) {
        Car car = carRepository.findByID(carID);
        return car;
    }
    @Override
    public void update(String carID, Car car) {
        // todo auto generated method stub
        carRepository.update(carID, car);
    }
    @Override
    public void deleteCarByID(String carID) {
        // todo auto generated method stub
        carRepository.delete(carID);
    }
}
