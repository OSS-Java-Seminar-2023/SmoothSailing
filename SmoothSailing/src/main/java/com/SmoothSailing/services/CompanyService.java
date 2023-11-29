package com.SmoothSailing.services;

import com.SmoothSailing.models.CompanyModel;
import com.SmoothSailing.repositories.CompanyRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    public final CompanyRepo companyRepo;

    @Autowired
    public CompanyService(CompanyRepo companyRepo){
        this.companyRepo=companyRepo;
    }

    public CompanyModel registerCompany(CompanyModel companyModel){
        if(companyRepo.findByEmail(companyModel.getEmail()).isPresent()){
            System.out.println("Email vec postoji u bazi!");
            return null;
        }
        companyModel.setPassword(BCrypt.hashpw(companyModel.getPassword(), BCrypt.gensalt(10)));
        return companyRepo.save(companyModel);
    }

    public CompanyModel authenticateCompany(String email, String password){
        System.out.println("Authenticating company with email: " + email);
        System.out.println("Authenticating company with password: " + password);

        Optional<CompanyModel> companyOptional = companyRepo.findByEmail(email);

        if (companyOptional.isPresent()){
            CompanyModel company = companyOptional.get();
            if(BCrypt.checkpw(password, company.getPassword())){
                return company;
            }
        }
        return null;
    }

    public List<CompanyModel> getAllCompanies(){
        return companyRepo.findAll();
    }
}