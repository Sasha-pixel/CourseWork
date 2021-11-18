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

    @OneToMany(mappedBy = "truck",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private List<Contract> contracts;

    @OneToOne(mappedBy = "car")
    private Osago osago;

    @OneToOne(mappedBy = "car")
    private User owner;
}
