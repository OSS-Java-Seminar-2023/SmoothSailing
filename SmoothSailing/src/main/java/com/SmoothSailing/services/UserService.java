package com.SmoothSailing.services;

import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.repositories.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    public final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo=userRepo;
    }

    public UserModel registerUser(String name, String surname, String password, String email, Date birthday, String gender, String license){
        if(userRepo.findByEmail(email).isPresent()){
            System.out.println("Email vec postoji u bazi!");
            return null;
        }
        if (name !=null && surname!=null && password!=null && email!=null){
            UserModel userModel = new UserModel();
            userModel.setName(name);
            userModel.setSurname(surname);
            userModel.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(10)));
            userModel.setEmail(email);
            userModel.setBirthday(birthday);
            userModel.setGender(gender);
            userModel.setLicense(license);
            return userRepo.save(userModel);
        }
        else{
            return null;
        }
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
