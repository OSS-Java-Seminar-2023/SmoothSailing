package com.SmoothSailing.controllers;

import com.SmoothSailing.repositories.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ReservationController {
    private final ReservationRepo reservationRepo;

    @Autowired
    public ReservationController(ReservationRepo reservationRepo){
        this.reservationRepo=reservationRepo;
    }
}