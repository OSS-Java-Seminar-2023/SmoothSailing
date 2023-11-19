package com.SmoothSailing.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name="Crew")
public class CrewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String position;
    @Column
    private String price;
    @Column
    private String review;

}