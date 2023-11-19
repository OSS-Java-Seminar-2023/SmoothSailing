package com.SmoothSailing.services;

import com.SmoothSailing.repositories.BoatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoatService {
    public final BoatRepo boatRepo;

    @Autowired
    public BoatService(BoatRepo boatRepo){
        this.boatRepo=boatRepo;
    }
}