package net.hostelHub.service.booking;

import net.hostelHub.dto.Response;
import net.hostelHub.dto.booking.BookingRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    ResponseEntity<Response> makeBooking(BookingRequest bookingRequest);
    ResponseEntity<List<RoomResponseDto>> fetchAvailableRooms(BookingRequest bookingRequest);
}
