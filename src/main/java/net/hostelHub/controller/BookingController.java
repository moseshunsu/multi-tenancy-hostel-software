package net.hostelHub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.booking.BookingRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import net.hostelHub.entity.booking.Booking;
import net.hostelHub.exception.UserNotFoundException;
import net.hostelHub.payment.dto.InitializePaymentResponse;
import net.hostelHub.payment.dto.PaymentVerificationResponse;
import net.hostelHub.service.booking.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookings")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Booking Controller REST APIs/Endpoint",
        description = "This controller includes endpoints which allow occupants book a hostel of their choice and " +
                "also make payments"
)
public class BookingController {
    private final BookingService bookingService;

    @Operation(
            description = "Booking endpoint for students",
            summary = "This endpoint allows students or occupant make booking to a room of a particular hostel",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "User not found",
                            responseCode = "500"
                    ),
                    @ApiResponse(
                            description = "Room not found",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Response> makeBooking(@RequestBody @Valid BookingRequest bookingRequest)
            throws UserNotFoundException {
        return bookingService.makeBooking(bookingRequest);
    }

    @Operation(
            description = "Endpoint for viewing available rooms",
            summary = "This endpoint exposes rooms marked as available, that is, rooms which not occupied or under " +
                    "maintenance, for hostel in a particular school. Fields required are school and hostel name",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomResponseDto>> fetchAvailableRooms(@RequestBody BookingRequest bookingRequest) {
        return bookingService.fetchAvailableRooms(bookingRequest);
    }

    @Operation(
            description = "Endpoint for viewing bookings made",
            summary = "This endpoint allow users retrieve a list of their associated bookings",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "No Content",
                            responseCode = "204"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Booking>> listOfBookings(@RequestParam String uniqueCode) {
        return bookingService.listOfBookings(uniqueCode);
    }

    @Operation(
            description = "Endpoint for approving bookings pending requests",
            summary = "This endpoint allows property managers approve a pending request upon successful payment.\n" +
                    "This endpoint also checks to make sure that a room is not booked beyond its capacity",
            responses = {
                    @ApiResponse(
                            description = "Approved",
                            responseCode = "202"
                    ),
                    @ApiResponse(
                            description = "No booking found",
                            responseCode = "500"
                    )
            }
    )
    @PutMapping("/{uniqueBookingNumber}")
    public ResponseEntity<?> approveBooking (@PathVariable String uniqueBookingNumber) {
        return bookingService.approveBooking(uniqueBookingNumber);
    }

    @Operation(
            description = "Endpoint for initializing payments",
            summary = "This endpoint allows occupant makes payment via pay stack gateway",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "No booking found",
                            responseCode = "500"
                    )
            }
    )
    @PostMapping("/payment")
    public ResponseEntity<InitializePaymentResponse> initializePayment(@RequestParam String uniqueBookingNumber) {
        return bookingService.initializePayment(uniqueBookingNumber);
    }

    @Operation(
            description = "Endpoint for verifying payments",
            summary = "This endpoint allow payments verification",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid reference number",
                            responseCode = "500"
                    )
            }
    )
    @GetMapping("/payment")
    public ResponseEntity<PaymentVerificationResponse> paymentVerification(@RequestParam String reference)
            throws Exception {
        return bookingService.paymentVerification(reference);
    }

}
