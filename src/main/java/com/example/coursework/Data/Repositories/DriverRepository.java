package com.example.coursework.Data.Repositories;

import com.example.coursework.Data.Entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Driver findByLicenseNumber(String licenseNumber);
}
