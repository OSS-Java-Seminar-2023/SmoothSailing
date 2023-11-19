package com.SmoothSailing.controllers;

import com.SmoothSailing.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo){
        this.userRepo=userRepo;
    }
}