package com.SmoothSailing.services;

import com.SmoothSailing.models.BoatModel;
import com.SmoothSailing.repositories.BoatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatService {
    public final BoatRepo boatRepo;

    @Autowired
    public BoatService(BoatRepo boatRepo){
        this.boatRepo=boatRepo;
    }

    public List<BoatModel> getAllBoats(){
        return boatRepo.findAll();
    }

    public BoatModel registerBoat(BoatModel boatModel){
        return boatRepo.save(boatModel);
    }
}