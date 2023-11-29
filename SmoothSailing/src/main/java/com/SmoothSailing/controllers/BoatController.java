package com.SmoothSailing.controllers;

import com.SmoothSailing.models.BoatModel;
import com.SmoothSailing.models.CompanyModel;
import com.SmoothSailing.repositories.BoatRepo;
import com.SmoothSailing.services.BoatService;
import com.SmoothSailing.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class BoatController {
    @Autowired
    private final BoatRepo boatRepo;
    @Autowired
    private BoatService boatService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    public BoatController(BoatRepo boatRepo){
        this.boatRepo=boatRepo;
    }

    @GetMapping(path="/boat/register")
    public String getRegisterBoat(Model model){
        model.addAttribute("registerBoatRequest", new BoatModel());
        model.addAttribute("companies", companyService.getAllCompanies());
        return "register_boat";
    }

    @PostMapping(path="/boat/register")
    public String registerBoat(@ModelAttribute BoatModel boatModel){
        BoatModel registeredBoat= boatService.registerBoat(boatModel);
        return registeredBoat == null ? "error_page" : "redirect:/";
    }
}