package com.SmoothSailing.repositories;

import com.SmoothSailing.models.CrewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CrewRepo extends JpaRepository<CrewModel, String> {

    List<CrewModel> findAllByPosition(String position);

}