package com.SmoothSailing.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Table(name="crewreservation")
public class CrewReservationModel {
    @ManyToOne
    @JoinColumn(name = "crew_id")
    private CrewModel crew_id;
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private ReservationModel reservation_id;
}
