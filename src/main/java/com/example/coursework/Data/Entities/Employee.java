package com.example.coursework.Data.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public", name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH},
            mappedBy = "employee")
    private List<Contract> contracts;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, List<Contract> contracts) {
        this.name = name;
        this.contracts = contracts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
