package com.boot.roomreservation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("guestservice")
public interface GuestClient {

    @GetMapping("/api/guests")
    List<Guest> getAllGuests();
    @GetMapping("api/guests/{id}")
    Guest getGuestById(@PathVariable("id") long id);
}
