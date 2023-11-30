package com.SmoothSailing.repositories;

import com.SmoothSailing.models.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepo extends JpaRepository<ReservationModel, String> {
}