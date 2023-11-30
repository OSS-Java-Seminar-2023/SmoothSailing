package com.SmoothSailing.services;

import com.SmoothSailing.dto.UserRegisterDto;
import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.repositories.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    public UserRepo userRepo;

    public UserModel registerUser(UserRegisterDto userModel){
        if(userRepo.findByEmail(userModel.getEmail()).isPresent()){
            System.out.println("Email vec postoji u bazi!");
            return null;
        }
        UserModel newUserModel = new UserModel();
        newUserModel.setName(userModel.getName());
        newUserModel.setSurname(userModel.getSurname());
        newUserModel.setEmail(userModel.getEmail());
        newUserModel.setPassword(BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt(10)));
        newUserModel.setGender(userModel.getGender());
        newUserModel.setLicense(userModel.getLicense());
        newUserModel.setBirthday(userModel.getBirthday());
        return userRepo.save(newUserModel);
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

    public String deleteUserById(String id){
        userRepo.deleteById(id);
        return "Success";
    }
}
