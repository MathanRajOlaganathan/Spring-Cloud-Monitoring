package com.boot.roomreservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/room-reservation")
public class RoomReservationController {

    private static final SimpleDateFormat  DATE_FORMAT = new SimpleDateFormat("yyy-MM-dd");

    private final RoomClient roomClient;

    private final ReservationClient reservationClient;

    private final GuestClient guestClient;

    @Autowired
    public RoomReservationController(RoomClient roomClient, ReservationClient reservationClient, GuestClient guestClient) {
        this.roomClient = roomClient;
        this.reservationClient = reservationClient;
        this.guestClient = guestClient;
    }

    //    private RestTemplate restTemplate;
//
//    @Autowired
//    public RoomReservationController(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    @GetMapping("/rooms/{id}")
    public List<RoomReservation> getAllRoomReservations(@PathVariable("id") long id)
    {
//    List<Room> rooms = getAllRooms();
        List<Room> rooms = roomClient.getAllRooms();
    List<RoomReservation> reservationList = new ArrayList<>();
    rooms.stream().forEach(room -> {
        Reservation reservation = reservationClient.getReservation(id);
        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setRoomName(room.getName());
        roomReservation.setRoomNumber(room.getRoomNumber());
        roomReservation.setRoomId(room.getId());
        roomReservation.setGuestId(reservation.getGuestId());
        reservationList.add(roomReservation);
    });
    return  reservationList;
    }
    @GetMapping("/guests")
    public List<Guest> getAllGuests()
    {
        return guestClient.getAllGuests();
    }


    @GetMapping()
    public List<RoomReservation> getAllRoomsByDate(@RequestParam(name="date",required=false) String dateString)
    {
        Date date = createDateFromDateString(dateString);
        System.out.println("date: "+ new java.sql.Date(date.getTime()));
        List<Room> rooms = roomClient.getAllRooms();
        Map<Long,RoomReservation> roomReservations = new HashMap<>();
        rooms.stream().forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservation.setRoomId(room.getId());
            roomReservations.put(room.getId(),roomReservation);
        });
        List<Reservation> reservation =reservationClient.getAllReservations(new java.sql.Date(date.getTime()));
        reservation.stream().forEach(reserv ->
        {
            RoomReservation roomReservation = roomReservations.get(reserv.getRoomId());
            Guest guest = guestClient.getGuestById(roomReservation.getGuestId());
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getId());
            roomReservation.setDate(date);

        });
        return new ArrayList<>(roomReservations.values());
    }

    private Date createDateFromDateString(String dateString)
    {
        Date date =null;
        if (null!=dateString)
        {
            try {
                date = DATE_FORMAT.parse(dateString);
            } catch (ParseException e) {
                date = new Date();
            }}
            else{
                date=new Date();
        }
            return date;
    }
//
//    private List<Room> getAllRooms()
//    {
//        ResponseEntity<List<Room>> roomReseponse = restTemplate.exchange("http://ROOMSERVICE/api/rooms", HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Room>>() {
//                });
//        return roomReseponse.getBody();
//    }


}
