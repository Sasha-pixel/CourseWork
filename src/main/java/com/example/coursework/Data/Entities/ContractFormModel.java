package com.example.coursework.Data.Entities;

import javax.persistence.Column;

public class ContractFormModel {

    private String carNumber;

    private String model;

    private int yearOfManufacture;

    private int power;

    private String vehicleIdentificationNumber;

    private String licenseNumber;

    private int drivingExperience;

    private int price;

    public ContractFormModel() {
    }

    public ContractFormModel(String carNumber, String model, int yearOfManufacture, int power, String vehicleIdentificationNumber, String licenseNumber, int drivingExperience, int price) {
        this.carNumber = carNumber;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
        this.power = power;
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.licenseNumber = licenseNumber;
        this.drivingExperience = drivingExperience;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
