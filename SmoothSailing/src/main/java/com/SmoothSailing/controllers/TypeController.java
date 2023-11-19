package com.SmoothSailing.controllers;

import com.SmoothSailing.repositories.TypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TypeController {
    private final TypeRepo typeRepo;

    @Autowired
    public TypeController(TypeRepo typeRepo){
        this.typeRepo=typeRepo;
    }
}