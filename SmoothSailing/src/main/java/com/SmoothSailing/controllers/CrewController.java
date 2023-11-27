package com.SmoothSailing.controllers;

import com.SmoothSailing.models.CrewModel;
import com.SmoothSailing.repositories.CrewRepo;
import com.SmoothSailing.services.CrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/crew")
@CrossOrigin
public class CrewController {
    @Autowired
    private CrewService crewService;
    @PostMapping(path="/register")
    public String registerCrew(@RequestBody CrewModel crewModel){
        System.out.println(crewModel.getName() + crewModel.getSurname() + crewModel.getPosition() + crewModel.getPrice());
        crewService.addCrewMemberInDB(crewModel);
        return "req 200";
    }

}