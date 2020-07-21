package com.boot.roomreservation;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("roomservice")
public interface RoomClient {

    @GetMapping("/api/rooms")
    List<Room> getAllRooms();
    @GetMapping("/api/roooms/{id}")
    Room getRoomById(@PathVariable("id") long id);

}
