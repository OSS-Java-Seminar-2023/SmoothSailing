package com.SmoothSailing.services;

import com.SmoothSailing.repositories.CrewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrewService {
    public final CrewRepo crewRepo;

    @Autowired
    public CrewService(CrewRepo crewRepo){
        this.crewRepo=crewRepo;
    }
}