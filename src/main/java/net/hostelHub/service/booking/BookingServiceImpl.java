package net.hostelHub.service.booking;

import lombok.RequiredArgsConstructor;
import net.hostelHub.dto.Data;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.booking.BookingRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import net.hostelHub.email.dto.EmailDetails;
import net.hostelHub.email.service.EmailService;
import net.hostelHub.entity.User;
import net.hostelHub.entity.booking.Booking;
import net.hostelHub.entity.room.Room;
import net.hostelHub.exception.NoSuchElementException;
import net.hostelHub.exception.UserNotFoundException;
import net.hostelHub.payment.dto.InitializePaymentDto;
import net.hostelHub.payment.dto.InitializePaymentResponse;
import net.hostelHub.payment.dto.PaymentVerificationResponse;
import net.hostelHub.payment.service.PayStackService;
import net.hostelHub.repository.UserRepository;
import net.hostelHub.repository.booking.BookingRepository;
import net.hostelHub.repository.room.RoomRepository;
import net.hostelHub.service.room.RoomService;
import net.hostelHub.utils.ResponseUtils;
import net.hostelHub.utils.RoomStatus;
import net.hostelHub.utils.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static net.hostelHub.utils.ResponseUtils.generateClientCode;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final RoomRepository roomRepository;
    private final PayStackService payStackService;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public ResponseEntity<Response> makeBooking(BookingRequest bookingRequest) throws UserNotFoundException {

        User occupant = userRepository.findAll()
                .stream()
                .filter( user -> user.getUniqueCode().equals(bookingRequest.getUniqueOccupantCode()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("user not found with unique code: " +
                        bookingRequest.getUniqueOccupantCode()));


        ResponseEntity<RoomResponseDto> fetchedRoomDetails = roomService.fetchRoomDetails(bookingRequest.getSchoolName(),
                bookingRequest.getHostelName(), bookingRequest.getRoomNumber());

        RoomResponseDto fetchedRoomDetailsBody = fetchedRoomDetails.getBody();
        
        boolean isRoomAvailable = false;
        if (fetchedRoomDetailsBody != null) {
            isRoomAvailable = fetchedRoomDetailsBody.getRoomStatus().equals(RoomStatus.AVAILABLE.name());
        }

        if (fetchedRoomDetailsBody != null && isRoomAvailable) {
            Booking booking = new Booking();
            booking.setUniqueBookingNumber(generateClientCode(ResponseUtils.LENGTH_OF_UNIQUE_BOOKING_NUMBER));
            booking.setHostelName(fetchedRoomDetailsBody.getHostelName());
            booking.setSchoolName(fetchedRoomDetailsBody.getSchoolName());
            booking.setUniqueOccupantCode(occupant.getUniqueCode());
            booking.setOccupantEmail(bookingRequest.getOccupantEmail());
            booking.setUniqueManagerCode(fetchedRoomDetailsBody.getUniqueCode());
            booking.setHostelContactEMail(fetchedRoomDetailsBody.getHostelContactEmail());
            booking.setAcademicYear(bookingRequest.getAcademicYear());
            booking.setRoomNumber(fetchedRoomDetailsBody.getRoomNumber());
            booking.setPrice(fetchedRoomDetailsBody.getPricePerBed());
            booking.setStatus(Status.PENDING);

            Booking savedbooking = bookingRepository.save(booking);

            String subject = "SUCCESSFUL BOOKING";
            String occupantMessage = "Booking successful, pending payment and approval from admin!";
            String hostelMessage = "Booking made, check booking table!";

            emailService.sendSimpleMail(createEmailDetails(subject, occupant.getEmail(), occupantMessage));
            emailService.sendSimpleMail(createEmailDetails(subject,
                                                            fetchedRoomDetailsBody.getHostelContactEmail(),
                                                            hostelMessage));

            return new ResponseEntity<>(
                    Response.builder()
                            .responseCode(ResponseUtils.BOOKING_SUCCESS_CODE)
                            .responseMessage(ResponseUtils.BOOKING_SUCCESS_MESSAGE)
                            .data(
                                    Data.builder()
                                            .uniqueCode(savedbooking.getUniqueOccupantCode())
                                            .email(savedbooking.getOccupantEmail())
                                            .build()
                            )
                            .build(),
                    HttpStatus.CREATED);
        } else  return ResponseEntity.badRequest().body(
                Response.builder()
                        .responseCode(ResponseUtils.ROOM_NOT_FOUND_CODE)
                        .responseMessage(ResponseUtils.ROOM_NOT_FOUND_MESSAGE)
                        .data(
                                Data.builder()
                                        .uniqueCode(bookingRequest.getUniqueOccupantCode())
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

    @Override
    public ResponseEntity<List<Booking>> listOfBookings(String uniqueCode) {
        List<Booking> bookingList = bookingRepository.findAll()
                .stream()
                .filter(booking -> booking.getUniqueManagerCode().equals(uniqueCode) ||
                                   booking.getUniqueOccupantCode().equals(uniqueCode))
                .toList();

        return !bookingList.isEmpty() ? ResponseEntity.ok(bookingList) : ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> approveBooking (String uniqueBookingNumber) {
        return bookingRepository.findAll()
                .stream()
                .filter(booking -> booking.getUniqueBookingNumber().equals(uniqueBookingNumber))
                .findAny()
                .map(booking -> {
                    boolean updateStatus = updateRoomStatus(booking);
                    if (!updateStatus) {
                        return ResponseEntity.noContent().build();
                    }
                    booking.setStatus(Status.APPROVED);
                    bookingRepository.save(booking);

                    String subject = "BOOKING APPROVED";
                    String recipient = booking.getOccupantEmail();
                    String message = "Congrats, your booking has been approved!";
                    emailService.sendSimpleMail(createEmailDetails(subject, recipient, message));

                    return ResponseEntity.accepted().body(booking);
                })
                .orElseThrow( () -> new NoSuchElementException("No such booking found!!! - " + uniqueBookingNumber));
    }

    @Override
    public ResponseEntity<InitializePaymentResponse> initializePayment(String uniqueBookingNumber) {
        Booking fetchedBooking = bookingRepository.findAll()
                .stream()
                .filter(booking -> booking.getUniqueBookingNumber().equalsIgnoreCase(uniqueBookingNumber))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("No booking found for " + uniqueBookingNumber));

        InitializePaymentDto paymentDto = InitializePaymentDto.builder()
                .amount(BigDecimal.valueOf(fetchedBooking.getPrice()))
                .email(email)
                .build();

        return new ResponseEntity<>(payStackService.initializePayment(paymentDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PaymentVerificationResponse> paymentVerification(String reference) throws Exception {
        return ResponseEntity.ok().body(payStackService.paymentVerification(reference));
    }

    private boolean updateRoomStatus(Booking booking) {
        Room fetchedRoom = roomRepository.findAll()
                .stream()
                .filter(room -> room.getUniqueCode().equals(booking.getUniqueManagerCode()) &&
                        room.getHostelName().equals(booking.getHostelName()) &&
                        room.getSchoolName().equals(booking.getSchoolName())  &&
                        room.getRoomNumber().equals(booking.getRoomNumber())
                )
                .findFirst()
                .get();

        if (fetchedRoom.getRoomStatus().equals(RoomStatus.AVAILABLE)) {
            fetchedRoom.setBedAvailable(fetchedRoom.getBedAvailable() - 1);
            if (fetchedRoom.getBedAvailable() == 0) {
                fetchedRoom.setRoomStatus(RoomStatus.OCCUPIED);
            }
            roomRepository.save(fetchedRoom);
            return true;
        }
        return false;
    }

    private EmailDetails createEmailDetails(String subject, String recipient, String messageBody) {
        return EmailDetails.builder()
                .subject(subject)
                .recipient(recipient)
                .messageBody(messageBody)
                .build();
    }

}
