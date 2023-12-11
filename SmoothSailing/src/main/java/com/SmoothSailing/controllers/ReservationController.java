package com.SmoothSailing.controllers;

import com.SmoothSailing.models.BoatModel;
import com.SmoothSailing.models.ReservationModel;
import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.repositories.BoatRepo;
import com.SmoothSailing.repositories.ReservationRepo;
import com.SmoothSailing.repositories.UserRepo;
import com.SmoothSailing.services.BoatService;
import com.SmoothSailing.services.ReservationService;
import com.SmoothSailing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ReservationController {
    @Autowired
    private final ReservationRepo reservationRepo;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private BoatService boatService;
    @Autowired
    private BoatRepo boatRepo;
    @Autowired
    private UserService userService;
    @Autowired
    public UserRepo userRepo;

    @Autowired
    public ReservationController(ReservationRepo reservationRepo){
        this.reservationRepo=reservationRepo;
    }

    @GetMapping("/company/reservations")
    public String getCompanyReservations(@CookieValue(name = "company_id", required = false) String id, Model model){
        if(id == null || id.isEmpty()){
            return "company/login_company_page";
        }
        List<BoatModel> boatModels = boatRepo.findAllByCompanyID(id);

        List<ReservationModel> reservations = new ArrayList<>();
        for (BoatModel boatModel : boatModels) {
            reservations.addAll(reservationRepo.findAllByBoat(boatModel.getId()));
        }

        model.addAttribute("reservations", reservations);
        return "company/company_reservations";
    }

    @PostMapping("/company/reservation/confirm")
    public String confirmReservation(@CookieValue(name = "company_id", required = false) String id,@RequestParam("id") String reservation_id){
        if(id == null || id.isEmpty()){
            return "company/login_company_page";
        }
        Optional <ReservationModel> optionalReservationModel = reservationRepo.findById(reservation_id);
        ReservationModel reservationModel = optionalReservationModel.get();
        reservationModel.setStatus("Confirmed");
        reservationService.saveReservation(reservationModel);

        return "redirect:/company/reservations";
    }

    @GetMapping("/user/reservation")
    public String getReservationPage(@CookieValue(name = "id", required = false) String id, Model model){
        if(id == null || id.isEmpty()){
            return "user/login_page";
        }
        System.out.println("ID cookie value: " + id);
        model.addAttribute("reservationRequest", new ReservationModel());
        model.addAttribute("boats", boatService.getAllBoats());
        model.addAttribute("users", userService.getAllUsers());
        return "user/reservation_page";
    }

    @PostMapping("/user/reservation")
    public String makeReservation(@CookieValue(name = "id", required = false) String id,ReservationModel reservationModel){
        Optional<UserModel> optionalUserModel = userRepo.findById(id);
        UserModel userModel = optionalUserModel.get();
        reservationModel.setUser_id(userModel);

        System.out.println("Reservation request: " + reservationModel);
        reservationModel.setStatus("Pending");

        ReservationModel reservationAttempt = reservationService.saveReservation(reservationModel);
        return reservationAttempt == null ? "error_page" : "redirect:/";
    }
}