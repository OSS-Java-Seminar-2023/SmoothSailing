package com.SmoothSailing.controllers;

import com.SmoothSailing.dto.ChangeUserPassDto;
import com.SmoothSailing.dto.UserLoginDto;
import com.SmoothSailing.dto.UserRegisterDto;
import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path="/list")
    public String users(Model model){
        List<UserModel> users = userService.getAllUsers();
        model.addAttribute("userListRequest", users);
        return "user_list";
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
    public String register(@ModelAttribute UserRegisterDto userDto){
        System.out.println("register request: " + userDto);
        UserModel registeredUser = userService.registerUser(userDto);
        return registeredUser == null ? "error_page" : "redirect:/user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDto userDto, Model model, HttpServletResponse response){
        System.out.println("login request: " + userDto);
        UserModel authenticated = userService.authenticate(userDto.getEmail(), userDto.getPassword());
        if(authenticated!=null){
            model.addAttribute("userEmail", authenticated.getEmail());

            Cookie cookie = new Cookie("user", authenticated.getId());
            cookie.setMaxAge(3600);
            response.addCookie(cookie);

            return "personal_page";
        }
        else{
            return "error_page";
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") String id, Model model){
        Optional<UserModel> user = userService.getUserById(id);
        user.ifPresent(userModel -> model.addAttribute("editUserRequest", userModel));
        return "edit_user";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, @ModelAttribute UserRegisterDto userDto){
        userService.editUser(id, userDto);
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id){
        userService.deleteUserById(id);
        return "redirect:/user/list";
    }

    @GetMapping("/change-password/{id}")
    public String changePassword(@PathVariable("id") String id, Model model){
        model.addAttribute("changePasswordRequest", id);
        return "change_password";
    }

    @PostMapping("/change-password/{id}")
    public String changePassword(@PathVariable("id") String id, @ModelAttribute ChangeUserPassDto changeUserPassDto){
        userService.changePass(id, changeUserPassDto.getPassword());
        return "redirect:/user/list";
    }
}
