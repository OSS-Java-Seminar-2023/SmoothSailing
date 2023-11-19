package com.SmoothSailing.controllers;

import com.SmoothSailing.repositories.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PaymentController {
    private final PaymentRepo paymentRepo;

    @Autowired
    public PaymentController(PaymentRepo paymentRepo){
        this.paymentRepo=paymentRepo;
    }
}