package com.SmoothSailing.services;

import com.SmoothSailing.repositories.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    public final ReservationRepo reservationRepo;

    @Autowired
    public ReservationService(ReservationRepo reservationRepo){
        this.reservationRepo=reservationRepo;
    }
}