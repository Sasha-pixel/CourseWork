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

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "osago_id")
    private Osago osago;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "price")
    private int price;

    @Column(name = "approved")
    private boolean approved;

    public Contract() {
    }

    public Contract(User customer, Employee employee, Driver driver, Car car, int price, boolean approved) {
        this.customer = customer;
        this.employee = employee;
        this.driver = driver;
        this.car = car;
        this.price = price;
        this.approved = approved;
    }

    public Contract(User customer, Date endDate, Date creationDate, Employee employee, Osago osago, Driver driver, Car car, int price, boolean approved) {
        this.customer = customer;
        this.endDate = endDate;
        this.creationDate = creationDate;
        this.employee = employee;
        this.osago = osago;
        this.driver = driver;
        this.car = car;
        this.price = price;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Osago getOsago() {
        return osago;
    }

    public void setOsago(Osago osago) {
        this.osago = osago;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
