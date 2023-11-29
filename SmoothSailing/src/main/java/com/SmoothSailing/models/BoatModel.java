package com.SmoothSailing.models;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="boats")
public class BoatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
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
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @Convert(converter = CompanyModelConverter.class)
    private CompanyModel company_id;
}