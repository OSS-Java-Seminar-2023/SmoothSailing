package com.SmoothSailing.services;

import com.SmoothSailing.dto.ChangeUserPassDto;
import com.SmoothSailing.dto.UserRegisterDto;
import com.SmoothSailing.models.BoatModel;
import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.repositories.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        newUserModel.setSuperuser(false);
        return userRepo.save(newUserModel);
    }

    public List<UserModel> getAll(int page){
        return userRepo.findAll(PageRequest.of(page, 5)).getContent();
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

    public Optional<UserModel> getUserById(String id){
        return userRepo.findById(id);
    }

    public void editUser(String id, UserRegisterDto user){
        userRepo.findById(id).map(userModel -> {
            userModel.setName(user.getName());
            userModel.setSurname(user.getSurname());
            userModel.setBirthday(user.getBirthday());
            userModel.setGender(user.getGender());
            userModel.setLicense(user.getLicense());
            return userRepo.save(userModel);
        });
    }

    public void changePass(String id, String password){
        userRepo.findById(id).map(userModel -> {
            userModel.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(10)));
            return userRepo.save(userModel);
        });
    }

    public void deleteUserById(String id){
        userRepo.deleteById(id);
    }
}
