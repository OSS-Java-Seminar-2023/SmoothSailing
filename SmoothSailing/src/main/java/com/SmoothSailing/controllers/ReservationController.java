package com.SmoothSailing.controllers;

import com.SmoothSailing.models.BoatModel;
import com.SmoothSailing.models.ReservationModel;
import com.SmoothSailing.repositories.ReservationRepo;
import com.SmoothSailing.services.BoatService;
import com.SmoothSailing.services.ReservationService;
import com.SmoothSailing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class ReservationController {
    @Autowired
    private final ReservationRepo reservationRepo;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private BoatService boatService;
    @Autowired
    private UserService userService;

    @Autowired
    public ReservationController(ReservationRepo reservationRepo){
        this.reservationRepo=reservationRepo;
    }

    @GetMapping("/reservation")
    public String getReservation(Model model){
        model.addAttribute("reservationRequest", new ReservationModel());
        model.addAttribute("boats", boatService.getAllBoats());
        model.addAttribute("users", userService.getAllUsers());
        return "reservation_page";
    }

    @PostMapping("/reservation")
    public String makeReservation(ReservationModel reservationModel){
        System.out.println("Reservation request: " + reservationModel);


        ReservationModel reservationAttempt = reservationService.saveReservation(reservationModel);
        return reservationAttempt == null ? "error_page" : "redirect:/company/login";
    }
}