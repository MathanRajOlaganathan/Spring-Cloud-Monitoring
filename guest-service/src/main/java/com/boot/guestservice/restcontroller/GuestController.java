package com.boot.guestservice.restcontroller;

import com.boot.guestservice.bean.Guest;
import com.boot.guestservice.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GuestController {

    private GuestRepository guestRepository;

    @Autowired
    public GuestController(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @GetMapping("/guests")
    public Iterable<Guest> getAllGuests()
    {
        return guestRepository.findAll();
    }

    @GetMapping("/guests/{id}")
    public Guest getGuestById(@PathVariable("id") long id)
    {
        return guestRepository.findById(id).orElse(new Guest());
    }

}
