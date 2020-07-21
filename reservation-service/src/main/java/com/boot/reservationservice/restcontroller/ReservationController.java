package com.boot.reservationservice.restcontroller;

import com.boot.reservationservice.bean.Reservation;
import com.boot.reservationservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.sql.Date;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public Iterable<Reservation> getReservations(@RequestParam(name="date", required = false) Date date){
        if(null!=date){
            return reservationRepository.findAllByDate(date);
        }
        return reservationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable("id") long id){
        return this.reservationRepository.findById(id).get();
    }

}
