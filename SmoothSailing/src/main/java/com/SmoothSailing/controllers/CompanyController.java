package com.SmoothSailing.controllers;

import com.SmoothSailing.dto.ChangeUserPassDto;
import com.SmoothSailing.dto.CompanyRegisterDto;
import com.SmoothSailing.dto.UserLoginDto;
import com.SmoothSailing.dto.UserRegisterDto;
import com.SmoothSailing.models.CompanyModel;
import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.repositories.CompanyRepo;
import com.SmoothSailing.services.CompanyService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/register")
    public String getRegisterCompanyPage(Model model){
        model.addAttribute("registerCompanyRequest", new CompanyModel());
        return "company/register_company_page";
    }

    @GetMapping("/login")
    public String getLoginCompanyPage(Model model){
        model.addAttribute("loginCompanyRequest", new CompanyModel());
        return "company/login_company_page";
    }

    @PostMapping("/register")
    public String registerCompany(@ModelAttribute CompanyRegisterDto companyRegisterDto){
        System.out.println("register request: " + companyRegisterDto);
        CompanyModel registeredCompany = companyService.registerCompany(companyRegisterDto);
        return registeredCompany == null ? "error_page" : "redirect:/company/login";
    }

    @PostMapping("/login")
    public String loginCompany(@ModelAttribute UserLoginDto companyLoginDto, Model model, HttpServletResponse response){
        System.out.println("login request: " + companyLoginDto);
        CompanyModel authenticated = companyService.authenticateCompany(companyLoginDto.getEmail(), companyLoginDto.getPassword());
        if (authenticated!=null){
            model.addAttribute("companyEmail", authenticated.getEmail());

            Cookie cookie = new Cookie("company_id", authenticated.getId());
            cookie.setMaxAge(3600);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "company/company_page";
        }
        else{
            return "error page";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("id", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "company/login_company_page";
    }

    @GetMapping(path="/list")
    public String users(Model model){
        List<CompanyModel> companies = companyService.getAllCompanies();
        model.addAttribute("companyListRequest", companies);
        return "company/company_list";
    }

    @GetMapping("/change-password/{id}")
    public String changePassword(@PathVariable("id") String id, Model model){
        model.addAttribute("changePasswordRequest", id);
        return "company/company_change_pass";
    }

    @PostMapping("/change-password/{id}")
    public String changePassword(@PathVariable("id") String id, @ModelAttribute ChangeUserPassDto changeUserPassDto){
        companyService.changePass(id, changeUserPassDto.getPassword());
        return "redirect:/company/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") String id, Model model){
        Optional<CompanyModel> user = companyService.getById(id);
        user.ifPresent(userModel -> model.addAttribute("editCompanyRequest", userModel));
        return "company/edit_company";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, @ModelAttribute CompanyRegisterDto companyDto){
        companyService.edit(id, companyDto);
        return "redirect:/company/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id){
        companyService.deleteById(id);
        return "redirect:/company/list";
    }
}