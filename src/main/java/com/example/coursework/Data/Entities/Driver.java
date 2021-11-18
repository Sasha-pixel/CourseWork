package com.example.coursework.Data.Entities;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driver_license_number")
    private String licenseNumber;

    @Column(name = "driving_experience")
    private int drivingExperience;

    @OneToOne(mappedBy = "driver")
    private User user;

    public Driver() {
    }

    public Driver(String licenseNumber, int drivingExperience, User user) {
        this.licenseNumber = licenseNumber;
        this.drivingExperience = drivingExperience;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getDrivingExperience() {
        return drivingExperience;
    }

    public void setDrivingExperience(int drivingExperience) {
        this.drivingExperience = drivingExperience;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
