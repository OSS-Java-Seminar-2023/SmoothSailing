package com.SmoothSailing.services;

import com.SmoothSailing.repositories.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public final PaymentRepo paymentRepo;

    @Autowired
    public PaymentService(PaymentRepo paymentRepo){
        this.paymentRepo=paymentRepo;
    }
}