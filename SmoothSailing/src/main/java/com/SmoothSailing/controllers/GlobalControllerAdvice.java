package com.SmoothSailing.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void checkCookie(@CookieValue(name = "name", required = false) String name, Model model) {
        if (name == null || name.isEmpty()) {
            name = "logout";
        }
        System.out.println(name);
        model.addAttribute("authorize", name);
    }
}