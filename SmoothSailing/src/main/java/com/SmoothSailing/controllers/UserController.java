package com.SmoothSailing.controllers;

import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path="/users")
    public List<UserModel> users(){
        return userService.getAllUsers();
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UserModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UserModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel){
        System.out.println("register request: " + userModel);
        UserModel registeredUser = userService.registerUser(userModel);
        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel, Model model){
        System.out.println("login request: " + userModel);
        UserModel authenticated = userService.authenticate(userModel.getEmail(), userModel.getPassword());
        if(authenticated!=null){
            model.addAttribute("userEmail", authenticated.getEmail());
            return "personal_page";
        }
        else{
            return "error_page";
        }
    }

}
