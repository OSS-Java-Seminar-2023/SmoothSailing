package com.SmoothSailing.repositories;

import com.SmoothSailing.models.BoatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoatRepo extends JpaRepository<BoatModel, UUID> {
}