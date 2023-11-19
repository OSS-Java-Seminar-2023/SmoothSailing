package com.SmoothSailing.controllers;

import com.SmoothSailing.repositories.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CompanyController {
    private final CompanyRepo companyRepo;

    @Autowired
    public CompanyController(CompanyRepo companyRepo){
        this.companyRepo=companyRepo;
    }
}