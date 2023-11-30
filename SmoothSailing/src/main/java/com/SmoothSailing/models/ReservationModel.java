package com.SmoothSailing.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Data
@Entity
@Table(name="reservation")
public class ReservationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Column
    private String startDate;
    @Column
    private String endDate;
    @Column
    private String downPayment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user_id;
    @ManyToOne
    @JoinColumn(name = "boat_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BoatModel boat_id;
}
