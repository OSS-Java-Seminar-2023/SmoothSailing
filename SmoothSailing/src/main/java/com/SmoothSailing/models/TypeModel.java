package com.SmoothSailing.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name="Type")
public class TypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private int crewCapacity;
    @Column
    private int passengerCapacity;
    @Column
    private float height;
    @Column
    private float width;
    @Column
    private float length;
}
