package com.example.coursework.Data.Entities;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "public", name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "creation_date")
    @CreatedDate
    private Date creationDate;

    @Column(name = "employee")
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "osago_id")
    private Osago osago;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "price")
    private int price;

    public Contract() {
    }


}
