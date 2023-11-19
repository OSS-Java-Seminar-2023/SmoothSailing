package com.SmoothSailing.services;

import com.SmoothSailing.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo=userRepo;
    }
}