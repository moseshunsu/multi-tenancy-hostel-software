package net.hostelHub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.booking.BookingRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import net.hostelHub.entity.booking.Booking;
import net.hostelHub.exception.UserNotFoundException;
import net.hostelHub.service.booking.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Response> makeBooking(@RequestBody @Valid BookingRequest bookingRequest)
            throws UserNotFoundException {
        return bookingService.makeBooking(bookingRequest);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomResponseDto>> fetchAvailableRooms(@RequestBody BookingRequest bookingRequest) {
        return bookingService.fetchAvailableRooms(bookingRequest);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> listOfBookings(@RequestParam String managerUniqueCode) {
        return bookingService.listOfBookings(managerUniqueCode);
    }

    @PutMapping("/{uniqueBookingNumber}")
    public ResponseEntity<?> approveBooking (@PathVariable String uniqueBookingNumber) {
        return bookingService.approveBooking(uniqueBookingNumber);
    }

}
