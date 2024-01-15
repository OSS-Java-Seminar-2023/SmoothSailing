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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        reservationService.checkReservationStatus(reservations);

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

    @PostMapping("/company/reservation/deny")
    public String denyReservation(@CookieValue(name = "company_id", required = false) String id,@RequestParam("id") String reservation_id){
        if(id == null || id.isEmpty()){
            return "company/login_company_page";
        }
        Optional <ReservationModel> optionalReservationModel = reservationRepo.findById(reservation_id);
        ReservationModel reservationModel = optionalReservationModel.get();
        reservationModel.setStatus("Denied");
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

        return "user/reservation_page";
    }

    @RequestMapping("/user/available_boats")
    public String getAvailableBoatsPage( @CookieValue(name = "id", required = false) String id,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date newStartDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date newEndDate,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(1);

        Page<BoatModel> boatPage = reservationService.findAvailableBoats(PageRequest.of(currentPage - 1, pageSize), newStartDate,newEndDate);

        model.addAttribute("reservationRequest", new ReservationModel());
        model.addAttribute("startDate", newStartDate);
        model.addAttribute("endDate", newEndDate);
        model.addAttribute("boatPage", boatPage);

        int totalPages = boatPage.getTotalPages();
        if(totalPages > 0) {
            List <Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        System.out.println("Start date is: " + newStartDate + " and End date is: " + newEndDate);

        return "user/available_boats";
    }

    @PostMapping("/user/make_reservation")
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