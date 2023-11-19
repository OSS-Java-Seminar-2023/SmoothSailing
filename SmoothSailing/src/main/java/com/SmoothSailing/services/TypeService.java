package com.SmoothSailing.services;

import com.SmoothSailing.repositories.TypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeService {
    public final TypeRepo typeRepo;

    @Autowired
    public TypeService(TypeRepo typeRepo){
        this.typeRepo=typeRepo;
    }
}