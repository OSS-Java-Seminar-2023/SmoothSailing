package com.SmoothSailing.repositories;

import com.SmoothSailing.models.CrewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CrewRepo extends JpaRepository<CrewModel, UUID> {

}