package net.hostelHub.service.booking;

import net.hostelHub.dto.Response;
import net.hostelHub.dto.booking.BookingRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import net.hostelHub.entity.booking.Booking;
import net.hostelHub.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    ResponseEntity<Response> makeBooking(BookingRequest bookingRequest) throws UserNotFoundException;
    ResponseEntity<List<RoomResponseDto>> fetchAvailableRooms(BookingRequest bookingRequest);
    ResponseEntity<List<Booking>> listOfBookings(String managerUniqueCode);
    ResponseEntity<?> approveBooking (String uniqueBookingNumber);
}
