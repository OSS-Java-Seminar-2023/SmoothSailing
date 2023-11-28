package com.SmoothSailing.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name="boat")
public class BoatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String img;
    @Column
    private String price;
    @Column
    private String availability;
    @Column
    private String review;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private int crewCapacity;
    @Column
    private int passengerCapacity;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyModel company_id;
}