package com.example.coursework.Data.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_number")
    private String carNumber;

    @Column(name = "model")
    private String model;

    @Column(name = "year_of_manufacture")
    private int yearOfManufacture;

    @Column(name = "power")
    private int power;

    @Column(name = "vin")
    private String vehicleIdentificationNumber;

    @OneToMany(mappedBy = "car",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private List<Contract> contracts;

    @OneToOne(mappedBy = "car")
    private Osago osago;

    @OneToOne(mappedBy = "car")
    private User owner;

    public Car() {
    }

    public Car(String carNumber, String model, int yearOfManufacture, int power, String vehicleIdentificationNumber, User owner) {
        this.carNumber = carNumber;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
        this.power = power;
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.owner = owner;
    }

    public Car(String carNumber, String model, int yearOfManufacture, int power, String vehicleIdentificationNumber, List<Contract> contracts, Osago osago, User owner) {
        this.carNumber = carNumber;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
        this.power = power;
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.contracts = contracts;
        this.osago = osago;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getVehicleIdentificationNumber() {
        return vehicleIdentificationNumber;
    }

    public void setVehicleIdentificationNumber(String vehicleIdentificationNumber) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public Osago getOsago() {
        return osago;
    }

    public void setOsago(Osago osago) {
        this.osago = osago;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
