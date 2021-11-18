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
}
