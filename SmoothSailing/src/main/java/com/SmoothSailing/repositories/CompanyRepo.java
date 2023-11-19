package com.SmoothSailing.repositories;

import com.SmoothSailing.models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyModel, UUID> {
}