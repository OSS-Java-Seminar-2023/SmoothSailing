package com.SmoothSailing.services;

import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.repositories.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserRepo userRepo;

    public UserModel registerUser(UserModel userModel){
        if(userRepo.findByEmail(userModel.getEmail()).isPresent()){
            System.out.println("Email vec postoji u bazi!");
            return null;
        }
        userModel.setPassword(BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt(10)));
        return userRepo.save(userModel);
    }

    public List<UserModel> getAllUsers(){
        List<UserModel> userModel = userRepo.findAll();
        return userModel;
    }

    public UserModel authenticate(String email, String password) {
        System.out.println("Authenticating user with email: " + email);
        System.out.println("Authenticating user with password: " + password);

        Optional<UserModel> userOptional = userRepo.findByEmail(email);

        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

}
