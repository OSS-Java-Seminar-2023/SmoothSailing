package com.SmoothSailing.controllers;


import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.repositories.UserRepo;
import com.SmoothSailing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserService userService, UserRepo userRepo){
        this.userService = userService;
        this.userRepo=userRepo;
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

    @PostMapping(path="/register")
    public String register(@RequestBody UserModel userModel){
        System.out.println("register request: " + userModel);
        UserModel registeredUser = userService.registerUser(userModel.getName(), userModel.getSurname(), userModel.getPassword(), userModel.getEmail(),userModel.getBirthday(),userModel.getGender(),userModel.getLicense());
        return "req 200";
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
