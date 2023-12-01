package com.SmoothSailing.services;

import com.SmoothSailing.dto.CompanyRegisterDto;
import com.SmoothSailing.dto.UserRegisterDto;
import com.SmoothSailing.models.CompanyModel;
import com.SmoothSailing.models.UserModel;
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

    public CompanyModel registerCompany(CompanyRegisterDto companyRegisterDto){
        if(companyRepo.findByEmail(companyRegisterDto.getEmail()).isPresent()){
            System.out.println("Email vec postoji u bazi!");
            return null;
        }
        CompanyModel companyModel = new CompanyModel();
        companyModel.setName(companyRegisterDto.getName());
        companyModel.setLocation(companyRegisterDto.getLocation());
        companyModel.setEmail(companyRegisterDto.getEmail());
        companyModel.setPassword(BCrypt.hashpw(companyRegisterDto.getPassword(), BCrypt.gensalt(10)));
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

    public void changePass(String id, String password){
        companyRepo.findById(id).map(userModel -> {
            userModel.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(10)));
            return companyRepo.save(userModel);
        });
    }

    public Optional<CompanyModel> getById(String id){
        return companyRepo.findById(id);
    }

    public void edit(String id, CompanyRegisterDto company){
        companyRepo.findById(id).map(companyModel -> {
            companyModel.setName(company.getName());
            companyModel.setLocation(company.getLocation());
            return companyRepo.save(companyModel);
        });
    }

    public void deleteById(String id){
        companyRepo.deleteById(id);
    }
}