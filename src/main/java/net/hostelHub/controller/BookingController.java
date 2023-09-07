package net.hostelHub.controller;

import net.hostelHub.dto.Response;
import net.hostelHub.dto.booking.BookingRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import net.hostelHub.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping
    public ResponseEntity<Response> makeBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.makeBooking(bookingRequest);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomResponseDto>> fetchAvailableRooms(@RequestBody BookingRequest bookingRequest) {
        return bookingService.fetchAvailableRooms(bookingRequest);
    }

}
