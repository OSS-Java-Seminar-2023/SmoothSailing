package com.SmoothSailing.controllers;

import com.SmoothSailing.repositories.BoatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BoatController {
    private final BoatRepo boatRepo;

    @Autowired
    public BoatController(BoatRepo boatRepo){
        this.boatRepo=boatRepo;
    }
}