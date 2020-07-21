package com.boot.roomreservation;
import java.sql.Date;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("reservationservice")
public interface ReservationClient {
    @GetMapping("/api/reservations")
    List<Reservation> getAllReservations(@RequestParam(name="date", required = false) Date date);

    @GetMapping("/api/reservations/{id}")
    Reservation getReservation(@PathVariable("id")long id);
}