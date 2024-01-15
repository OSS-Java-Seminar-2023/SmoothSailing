package com.SmoothSailing.services;

import com.SmoothSailing.dto.ReservationDatesDto;
import com.SmoothSailing.models.BoatModel;
import com.SmoothSailing.models.ReservationModel;
import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.repositories.BoatRepo;
import com.SmoothSailing.repositories.ReservationRepo;
import com.SmoothSailing.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReservationService {
    public final ReservationRepo reservationRepo;

    @Autowired
    public BoatRepo boatRepo;

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

    public ReservationModel saveReservation(ReservationModel reservationModel) {

        if (reservationModel.getStartDate().after(reservationModel.getEndDate())) {
            System.out.println("Start date cannot be after end date!");
            return null;
        }

        if(getAllReservationBoatID().contains(UUID.fromString(reservationModel.getBoat_id().getId()))){

            List <ReservationDatesDto> dateRows = getAllDatesByBoatID((reservationModel.getBoat_id().getId()));
            for (ReservationDatesDto dateRow : dateRows) {
                if( checkDatesOverlap(reservationModel.getStartDate(), reservationModel.getEndDate(), dateRow.getStartDate(), dateRow.getEndDate()) ){
                    System.out.println("There is already a reservation during this time!");
                    return null;
                }
            }
        }

        return reservationRepo.save(reservationModel);
    }

    public boolean checkDatesOverlap(Date newStartDate, Date newEndDate, Date existingStartDate, Date existingEndDate) {

        if ((newStartDate.after(existingStartDate) && newStartDate.before(existingEndDate))
                || (newEndDate.after(existingStartDate) && newEndDate.before(existingEndDate))
                || (newStartDate.before(existingStartDate) && newEndDate.after(existingEndDate))) {
            return true;
        }

        return false;
    }

    public List<ReservationModel> checkReservationStatus(List<ReservationModel> reservations){
        Date currentDate = new Date();

        for (ReservationModel reservation : reservations) {
            Date startDate = reservation.getStartDate();
            Date endDate = reservation.getEndDate();

            if (currentDate.after(startDate) && currentDate.before(endDate) && reservation.getStatus().equals("Confirmed")) {
                reservation.setStatus("In progress");
                reservationRepo.save(reservation);
            } else if(currentDate.after(startDate) && currentDate.before(endDate) && reservation.getStatus().equals("Pending")) {
                reservation.setStatus("Denied");
                reservationRepo.save(reservation);
            } else if (currentDate.after(endDate) && (reservation.getStatus().equals("Confirmed") || reservation.getStatus().equals("In progress"))) {
                reservation.setStatus("Concluded");
                reservationRepo.save(reservation);
            } else if (currentDate.after(endDate) && reservation.getStatus().equals("Pending")) {
                reservation.setStatus("Denied");
                reservationRepo.save(reservation);
            }
        }

        return reservations;
    }

    public Page<BoatModel> findAvailableBoats(Pageable pageable, Date newStartDate, Date newEndDate){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List <BoatModel> unavailableBoats = new ArrayList<>();

        List <BoatModel> availableBoats = new ArrayList<>();

        List <ReservationModel> reservations = reservationRepo.findAll();

        for (ReservationModel reservation : reservations) {
            if( checkDatesOverlap(newStartDate, newEndDate, reservation.getStartDate(), reservation.getEndDate()) ){
                unavailableBoats.add(reservation.getBoat_id());
            }
        }

        List<BoatModel> allBoats = boatRepo.findAll();

        for (BoatModel boat : allBoats) {
            if (!unavailableBoats.contains(boat)) {
                availableBoats.add(boat);
            }
        }

        List <BoatModel> list;

        if (availableBoats.size() < startItem){
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, availableBoats.size());
            list = availableBoats.subList(startItem, toIndex);
        }

        Page<BoatModel> boatPage = new PageImpl<BoatModel>(list, PageRequest.of(currentPage, pageSize), availableBoats.size());

        return boatPage;
    }
}