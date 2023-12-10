package com.SmoothSailing.services;

import com.SmoothSailing.dto.CrewDto;
import com.SmoothSailing.models.CompanyModel;
import com.SmoothSailing.models.CrewModel;
import com.SmoothSailing.repositories.CompanyRepo;
import com.SmoothSailing.repositories.CrewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrewService {
    @Autowired
    private CrewRepo crewRepo;

    @Autowired
    private CompanyRepo companyRepo;

    public CrewModel register(CrewDto crewDto){
        CompanyModel companyModel = companyRepo.getReferenceById(crewDto.getCompany_id());
        System.out.println(companyModel);
        CrewModel crewModel = new CrewModel();
        crewModel.setName(crewDto.getName());
        crewModel.setSurname(crewDto.getSurname());
        crewModel.setPosition(crewDto.getPosition().toLowerCase());
        crewModel.setPrice(crewDto.getPrice());
        crewModel.setReview(crewDto.getReview());
        crewModel.setCompany_id(companyModel);
        return crewRepo.save(crewModel);
    }

    public List<CrewModel> getMembers(String position){
        return crewRepo.findAllByPosition(position);
    }

    public List<CrewModel> getAll(){
        return crewRepo.findAll();
    }

    public Optional<CrewModel> byId(String id){
        return crewRepo.findById(id);
    }

    public void edit(String id, CrewDto crew){
        CompanyModel companyModel = new CompanyModel();
        companyModel.setId(crew.getCompany_id());
        crewRepo.findById(id).map(crewModel -> {
            crewModel.setName(crew.getName());
            crewModel.setSurname(crew.getSurname());
            crewModel.setPosition(crew.getPosition().toLowerCase());
            crewModel.setPrice(crew.getReview());
            crewModel.setReview(crew.getReview());
            crewModel.setCompany_id(companyModel);
            return crewRepo.save(crewModel);
        });
    }

    public void deleteById(String id){
        crewRepo.deleteById(id);
    }
}