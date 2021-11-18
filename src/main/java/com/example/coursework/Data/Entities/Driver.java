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
}
