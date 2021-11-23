package com.example.coursework.Validators;

import com.example.coursework.Data.Entities.Driver;
import com.example.coursework.Data.Repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public void saveDriver(Driver driver) {
        driverRepository.save(driver);
    }

    public Driver findByLicenseNumber(String licenseNumber) {
        return driverRepository.findByLicenseNumber(licenseNumber);
    }
}
