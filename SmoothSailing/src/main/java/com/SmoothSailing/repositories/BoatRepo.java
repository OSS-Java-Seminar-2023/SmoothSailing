package com.SmoothSailing.repositories;

import com.SmoothSailing.models.BoatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoatRepo extends JpaRepository<BoatModel, String> {

    @Query("SELECT b FROM BoatModel b WHERE b.company_id.id = :id")
    List<BoatModel> findAllByCompanyID(@Param("id") String id);
}