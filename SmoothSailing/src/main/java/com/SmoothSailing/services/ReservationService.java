package com.SmoothSailing.services;

import com.SmoothSailing.dto.ReservationDatesDto;
import com.SmoothSailing.models.BoatModel;
import com.SmoothSailing.models.ReservationModel;
import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.repositories.BoatRepo;
import com.SmoothSailing.repositories.ReservationRepo;
import com.SmoothSailing.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {
    public final ReservationRepo reservationRepo;

    @Autowired
    public ReservationService(ReservationRepo reservationRepo){
        this.reservationRepo=reservationRepo;
    }

    public List<UUID> getAllReservationBoatID() {
        return reservationRepo.findAllBoatID();
    }

    public List<ReservationDatesDto> getAllDatesByBoatID(String boat_id){
        return reservationRepo.findAllDatesByBoatID(boat_id);
    }

    public ReservationModel saveReservation(ReservationModel reservationModel){

        if(reservationModel.getStartDate().after(reservationModel.getEndDate())){
            System.out.println("Start date cannot be after end date!");
            return null;
        }

        if(getAllReservationBoatID().contains(UUID.fromString(reservationModel.getBoat_id().getId()))){

            List <ReservationDatesDto> dateRows = getAllDatesByBoatID((reservationModel.getBoat_id().getId()));
            for (ReservationDatesDto dateRow : dateRows) {
                if((reservationModel.getStartDate().after(dateRow.getStartDate()) && reservationModel.getStartDate().before(dateRow.getEndDate())) || (reservationModel.getEndDate().after(dateRow.getStartDate()) && reservationModel.getEndDate().before(dateRow.getEndDate())) || (reservationModel.getStartDate().before(dateRow.getStartDate()) && reservationModel.getEndDate().after(dateRow.getEndDate())) ){
                    System.out.println("There is already a reservation during this time!");
                    return null;
                }
            }
        }

        return reservationRepo.save(reservationModel);
    }
}