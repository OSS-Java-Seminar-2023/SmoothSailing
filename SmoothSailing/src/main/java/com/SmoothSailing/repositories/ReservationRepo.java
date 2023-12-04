package com.SmoothSailing.repositories;

import com.SmoothSailing.models.BoatModel;
import com.SmoothSailing.models.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepo extends JpaRepository<ReservationModel, String> {

    @Query(value="SELECT boat_id FROM reservation", nativeQuery = true)
    List<UUID> findAllBoatID();

    @Query(value="SELECT start_date, end_date FROM reservation WHERE boat_id = ?1", nativeQuery = true)
    List<Date[]> findAllDatesByBoatID(String boat_id);

}