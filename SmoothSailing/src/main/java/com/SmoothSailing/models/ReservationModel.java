package com.SmoothSailing.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name="Reservation")
public class ReservationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    private String startDate;
    @Column
    private String endDate;
    @Column
    private String downPayment;
}
