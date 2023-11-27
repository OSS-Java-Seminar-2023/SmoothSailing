package com.SmoothSailing.controllers;

import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path="/users")
    public List<UserModel> users(){
        return userService.getAllUsers();
    }

    @PostMapping(path="/register")
    public String register(@RequestBody UserModel userModel){
        UserModel registeredUser = userService.registerUser(userModel);
        return "req 200";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserModel userModel, Model model){
        System.out.println("login request: " + userModel);
        UserModel authenticated = userService.authenticate(userModel.getEmail(), userModel.getPassword());
        if(authenticated!=null){
            model.addAttribute("userEmail", authenticated.getEmail());
            System.out.println("User exist, authenticated!");
            return "req 200";
        }
        else{
            System.out.println("User doesn't exsit!");
            return "req 204";
        }
    }

}
