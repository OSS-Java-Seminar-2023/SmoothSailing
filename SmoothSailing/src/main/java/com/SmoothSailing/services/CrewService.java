package com.SmoothSailing.services;

import com.SmoothSailing.models.CrewModel;
import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.repositories.CrewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewService {
    @Autowired
    private CrewRepo crewRepo;
    public void addCrewMemberInDB(CrewModel crewModel){
        crewRepo.save(crewModel);
    }
}