package com.SmoothSailing.services;

import com.SmoothSailing.repositories.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    public final CompanyRepo companyRepo;

    @Autowired
    public CompanyService(CompanyRepo companyRepo){
        this.companyRepo=companyRepo;
    }
}