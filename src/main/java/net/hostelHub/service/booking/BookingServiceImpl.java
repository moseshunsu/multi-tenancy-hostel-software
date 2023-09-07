package net.hostelHub.service.booking;

import net.hostelHub.dto.Data;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.booking.BookingRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import net.hostelHub.email.dto.EmailDetails;
import net.hostelHub.entity.booking.Booking;
import net.hostelHub.repository.booking.BookingRepository;
import net.hostelHub.service.room.RoomService;
import net.hostelHub.utils.ResponseUtils;
import net.hostelHub.utils.RoomStatus;
import net.hostelHub.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
//    @Autowired
//    RabbitMQJsonProducer jsonProducer;
    @Autowired
    RoomService roomService;


    @Override
    public ResponseEntity<Response> makeBooking(BookingRequest bookingRequest) {

        ResponseEntity<RoomResponseDto> fetchedRoomDetails = roomService.fetchRoomDetails(bookingRequest.getSchoolName(),
                bookingRequest.getHostelName(), bookingRequest.getRoomNumber());

        RoomResponseDto fetchedRoomDetailsBody = fetchedRoomDetails.getBody();
        
        boolean isRoomAvailable = false;
        if (fetchedRoomDetailsBody != null) {
            isRoomAvailable = fetchedRoomDetailsBody.getRoomStatus().equals(RoomStatus.AVAILABLE.name());
        }

        if (fetchedRoomDetailsBody != null && isRoomAvailable) {
            Booking booking = new Booking();
            booking.setHostelName(fetchedRoomDetailsBody.getHostelName());
            booking.setSchool(fetchedRoomDetailsBody.getSchoolName());
            booking.setUniqueOccupantCode(bookingRequest.getUniqueCode());
            booking.setOccupantEmail(bookingRequest.getOccupantEmail());
            booking.setUniqueTenantCode(fetchedRoomDetailsBody.getUniqueCode());
            booking.setHostelContactMail(fetchedRoomDetailsBody.getHostelContactMail());
            booking.setAcademicYear(bookingRequest.getAcademicYear());
            booking.setRoomNumber(fetchedRoomDetailsBody.getRoomNumber());
            booking.setPrice(fetchedRoomDetailsBody.getPricePerBed());
            booking.setStatus(Status.PENDING);

            Booking savedbooking = bookingRepository.save(booking);

//            EmailDetails emailDetails = EmailDetails.builder()
//                    .subject("SUCCESSFUL BOOKING")
//                    .recipient("moses.hunsu@yahoo.com")
//                    .messageBody("Booking successful, pending payment and approval from admin!")
//                    .build();
//
//            jsonProducer.sendJsonMessage(emailDetails);

            return ResponseEntity.ok().body(
                    Response.builder()
                            .responseCode(ResponseUtils.BOOKING_SUCCESS_CODE)
                            .responseMessage(ResponseUtils.BOOKING_SUCCESS_MESSAGE)
                            .data(
                                    Data.builder()
                                            .uniqueCode(savedbooking.getUniqueOccupantCode())
                                            .email(savedbooking.getOccupantEmail())
                                            .build()
                            )
                            .build()
            );
        } else  return ResponseEntity.badRequest().body(
                Response.builder()
                        .responseCode(ResponseUtils.ROOM_NOT_FOUND_CODE)
                        .responseMessage(ResponseUtils.ROOM_NOT_FOUND_MESSAGE)
                        .data(
                                Data.builder()
                                        .uniqueCode(bookingRequest.getUniqueCode())
                                        .email(bookingRequest.getOccupantEmail())
                                        .build()
                        )
                        .build()
        );
    }

    // This allows occupants search for available rooms in a particular school
    @Override
    public ResponseEntity<List<RoomResponseDto>> fetchAvailableRooms(BookingRequest bookingRequest) {
        return roomService.fetchAvailableRooms(bookingRequest.getSchoolName(), bookingRequest.getHostelName());
    }

}
