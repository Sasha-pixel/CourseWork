package com.example.coursework.Validators;

import com.example.coursework.Data.Entities.Car;
import com.example.coursework.Data.Repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car findByCarNumberOrVehicleIdentificationNumber(String carNumber, String vehicleIdentificationNumber) {
        return carRepository.findByCarNumberOrVehicleIdentificationNumber(carNumber, vehicleIdentificationNumber);
    }

    public void saveCar(Car car) {
        carRepository.save(car);
    }
}
