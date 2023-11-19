package com.SmoothSailing.controllers;

import com.SmoothSailing.repositories.CrewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CrewController {
    private final CrewRepo crewRepo;

    @Autowired
    public CrewController(CrewRepo crewRepo){
        this.crewRepo=crewRepo;
    }
}