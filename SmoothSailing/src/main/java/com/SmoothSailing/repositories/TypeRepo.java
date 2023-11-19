package com.SmoothSailing.repositories;

import com.SmoothSailing.models.TypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TypeRepo extends JpaRepository<TypeModel, UUID> {
}