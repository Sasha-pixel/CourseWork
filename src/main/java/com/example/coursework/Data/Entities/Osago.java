package com.example.coursework.Data.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "public", name = "osago")
public class Osago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public Osago() {
    }

    public Osago(String number, Date startDate, Date endDate, Car car) {
        this.number = number;
        this.startDate = startDate;
        this.endDate = endDate;
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
