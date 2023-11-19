package com.SmoothSailing.repositories;

import com.SmoothSailing.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentModel, UUID> {
}