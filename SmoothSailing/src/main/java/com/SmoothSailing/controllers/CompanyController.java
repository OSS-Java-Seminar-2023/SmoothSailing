package com.SmoothSailing.controllers;

import com.SmoothSailing.models.CompanyModel;
import com.SmoothSailing.repositories.CompanyRepo;
import com.SmoothSailing.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    private final CompanyRepo companyRepo;

    @Autowired
    public CompanyController(CompanyRepo companyRepo){
        this.companyRepo=companyRepo;
    }

    @GetMapping("/register/company")
    public String getRegisterCompanyPage(Model model){
        model.addAttribute("registerCompanyRequest", new CompanyModel());
        return "register_company_page";
    }

    @GetMapping("/login/company")
    public String getLoginCompanyPage(Model model){
        model.addAttribute("loginCompanyRequest", new CompanyModel());
        return "login_company_page";
    }

    @PostMapping("/register/company")
    public String registerCompany(@ModelAttribute CompanyModel companyModel){
        System.out.println("register request: " + companyModel);
        CompanyModel registeredCompany = companyService.registerCompany(companyModel);
        return registeredCompany == null ? "error_page" : "redirect:/login/company";
    }

    @PostMapping("/login/company")
    public String loginCompany(@ModelAttribute CompanyModel companyModel, Model model){
        System.out.println("login request: " + companyModel);
        CompanyModel authenticated = companyService.authenticateCompany(companyModel.getEmail(), companyModel.getPassword());
        if (authenticated!=null){
            model.addAttribute("companyEmail", authenticated.getEmail());
            return "company_page";
        }
        else{
            return "error page";
        }
    }
}