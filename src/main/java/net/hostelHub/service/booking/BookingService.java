package net.hostelHub.service.booking;

import net.hostelHub.dto.Response;
import net.hostelHub.dto.booking.BookingRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import net.hostelHub.entity.booking.Booking;
import net.hostelHub.exception.UserNotFoundException;
import net.hostelHub.payment.dto.InitializePaymentDto;
import net.hostelHub.payment.dto.InitializePaymentResponse;
import net.hostelHub.payment.dto.PaymentVerificationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    ResponseEntity<Response> makeBooking(BookingRequest bookingRequest) throws UserNotFoundException;
    ResponseEntity<List<RoomResponseDto>> fetchAvailableRooms(BookingRequest bookingRequest);
    ResponseEntity<List<Booking>> listOfBookings(String managerUniqueCode);
    ResponseEntity<?> approveBooking (String uniqueBookingNumber);
    ResponseEntity<InitializePaymentResponse> initializePayment(String uniqueBookingNumber);
    ResponseEntity<PaymentVerificationResponse> paymentVerification(String reference) throws Exception;
}
