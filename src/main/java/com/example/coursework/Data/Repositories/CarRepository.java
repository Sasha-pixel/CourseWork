package com.example.coursework.Data.Repositories;

import com.example.coursework.Data.Entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByCarNumberOrVehicleIdentificationNumber(String carNumber, String vehicleIdentificationNumber);

    Car findCarById(Long id);
}
