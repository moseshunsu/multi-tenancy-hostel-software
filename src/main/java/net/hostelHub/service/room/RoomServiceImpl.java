package net.hostelHub.service.room;

import lombok.RequiredArgsConstructor;
import net.hostelHub.dto.Data;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.room.RoomRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import net.hostelHub.dto.room.RoomTypeRequest;
import net.hostelHub.entity.User;
import net.hostelHub.entity.properties.HostelProperty;
import net.hostelHub.entity.room.Room;
import net.hostelHub.entity.room.RoomType;
import net.hostelHub.exception.NoSuchElementException;
import net.hostelHub.exception.UserNotFoundException;
import net.hostelHub.repository.UserRepository;
import net.hostelHub.repository.properties.HostelPropertyRepository;
import net.hostelHub.repository.room.RoomRepository;
import net.hostelHub.repository.room.RoomTypeRepository;
import net.hostelHub.utils.ResponseUtils;
import net.hostelHub.utils.RoomStatus;
import net.hostelHub.utils.School;
import net.hostelHub.utils.Sex;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;
    private final HostelPropertyRepository hostelPropertyRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Response> addRoomType(RoomTypeRequest roomTypeRequest) {

        String hostelName = roomTypeRequest.getHostelName().trim();
        String schoolName = roomTypeRequest.getSchoolName().trim();

        boolean isRoomTypeExists = roomTypeRepository.findAll()
                .stream()
                .anyMatch(
                        roomType -> roomType.getHostelName().equalsIgnoreCase(hostelName) &&
                                    roomType.getSchoolName().equalsIgnoreCase(schoolName) &&
                                    roomType.getPricePerBed().equals(roomTypeRequest.getPricePerBed()) &&
                                    Objects.equals(roomType.getNumberInARoom(), roomTypeRequest.getNumberInARoom())
                );

        if (isRoomTypeExists) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .responseCode(ResponseUtils.ROOM_TYPE_EXISTS_CODE)
                            .responseMessage(ResponseUtils.ROOM_TYPE_EXISTS_MESSAGE)
                            .data(
                                    Data.builder()
                                            .uniqueCode(roomTypeRequest.getUniqueCode())
                                            .build()
                            )
                            .build()
            );
        }

        HostelProperty fetchedProperty = hostelPropertyRepository.findAll()
                .stream()
                .filter(hostelProperty ->
                                hostelName.equalsIgnoreCase(hostelProperty.getHostelName()) &&
                                schoolName.equalsIgnoreCase(hostelProperty.getSchoolName().name())
                ).findFirst()
                .orElseThrow(
                        () -> new NoSuchElementException(String.format("No %s in %s", hostelName, schoolName)))
                ;

        if (fetchedProperty != null) {
            RoomType roomType = new RoomType();
            roomType.setNumberInARoom(roomTypeRequest.getNumberInARoom());
            roomType.setHostelName(fetchedProperty.getHostelName());
            roomType.setSchoolName(fetchedProperty.getSchoolName().name());
            roomType.setUniqueCode(fetchedProperty.getUniqueCode());
            roomType.setPricePerBed(roomTypeRequest.getPricePerBed());
            roomType.setDescription(roomTypeRequest.getDescription());

            roomTypeRepository.save(roomType);

            return ResponseEntity.ok().body(
                    Response.builder()
                            .responseCode(ResponseUtils.SUCCESS_CODE)
                            .responseMessage(ResponseUtils.ROOM_TYPE_ENTRY_SUCCESS)
                            .data(
                                    Data.builder()
                                            .uniqueCode(fetchedProperty.getUniqueCode())
                                            .username(fetchedProperty.getSchoolName().name())
                                            .email(fetchedProperty.getContactEmail())
                                            .build()
                            )
                            .build()
            );
        }

            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .responseCode(ResponseUtils.HOSTEL_NOT_FOUND_CODE)
                            .responseMessage(ResponseUtils.HOSTEL_NOT_FOUND_MESSAGE + hostelName + " in " + schoolName)
                            .build()
            );
    }

    @Override
    public ResponseEntity<Response> addRoom(RoomRequest roomRequest) throws UserNotFoundException {

        boolean isRoomExists = roomRepository.existsById(roomRequest.getRoomNumber());

        if (isRoomExists) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .responseCode(ResponseUtils.ROOM_EXISTS_CODE)
                            .responseMessage(ResponseUtils.ROOM_EXISTS_MESSAGE + ": " + roomRequest.getRoomNumber())
                            .data(
                                    Data.builder()
                                            .uniqueCode(roomRequest.getUniqueCode())
                                            .build()
                            )
                            .build()
            );
        }

        User fetchedUser = userRepository.findAll()
                .stream()
                .filter(
                       user -> user.getUniqueCode().equals(roomRequest.getUniqueCode())
                )
                .findFirst()
                .orElseThrow(() ->
                        new UserNotFoundException("user not found with unique code: " + roomRequest.getUniqueCode())
                );

        RoomType roomType = roomTypeRepository.findAll()
                .stream()
                .filter(
                        type -> roomRequest.getHostelName().equalsIgnoreCase(type.getHostelName()) &&
                                roomRequest.getNumberInARoom().equals(type.getNumberInARoom()) &&
                                roomRequest.getUniqueCode().equals(type.getUniqueCode()) &&
                                roomRequest.getSchoolName().equalsIgnoreCase(type.getSchoolName())
                )
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Room type not found, recheck room type credentials"));

        Room room = new Room();
        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setRoomType(roomType);
        room.setUniqueCode(fetchedUser.getUniqueCode());
        room.setRoomStatus(RoomStatus.valueOf(roomRequest.getRoomStatus().toUpperCase()));
        room.setHostelName(roomType.getHostelName());
        room.setSchoolName(roomType.getSchoolName());
        room.setPricePerBed(roomType.getPricePerBed());
        room.setSex(Sex.valueOf(roomRequest.getSex().toUpperCase()));
        room.setBedAvailable(
                roomRequest.getRoomStatus().equals("MAINTENANCE") || roomRequest.getRoomStatus().equals("OCCUPIED")
                        ? 0
                        : room.getRoomType().getNumberInARoom()
        );

        Room savedRoom = roomRepository.save(room);

        return ResponseEntity.ok().body(
                Response.builder()
                        .responseCode(ResponseUtils.SUCCESS_CODE)
                        .responseMessage(ResponseUtils.ROOM_ENTRY_SUCCESS)
                        .data(
                                Data.builder()
                                        .email(savedRoom.getRoomType().getHostelName())
                                        .username(savedRoom.getRoomNumber())
                                        .uniqueCode(savedRoom.getRoomType().getUniqueCode())
                                        .build()
                        )
                        .build()
        );
    }

    // This allows occupants to fetch a specific room detail in a particular school
    @Override
    public ResponseEntity<RoomResponseDto> fetchRoomDetails(String schoolName, String hostelName, String roomNumber) {

        Optional<Room> roomOptional = roomRepository.findAll()
                .stream()
                .filter(room ->
                                room.getRoomType().getHostelName().equals(hostelName) &&
                                room.getRoomType().getSchoolName().equals(schoolName) &&
                                room.getRoomNumber().equals(roomNumber)
                )
                .findFirst();

        if (roomOptional.isPresent()) {

            Room room = roomOptional.get();
            RoomResponseDto roomDetails = RoomResponseDto.builder()
                    .hostelName(room.getRoomType().getHostelName())
                    .schoolName(room.getRoomType().getSchoolName())
                    .uniqueCode(room.getRoomType().getUniqueCode())
                    .hostelContactMail(hostelContactMail(schoolName, hostelName))
                    .roomStatus(room.getRoomStatus().name())
                    .pricePerBed(room.getRoomType().getPricePerBed())
                    .description(room.getRoomType().getDescription())
                    .roomNumber(room.getRoomNumber())
                    .sex(String.valueOf(room.getSex()))
                    .numberInARoom(room.getRoomType().getNumberInARoom())
                    .bedAvailable(room.getBedAvailable())
                    .build();

            return ResponseEntity.ok().body(roomDetails);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // This allows occupants search for available rooms in a particular school
    @Override
    public ResponseEntity<List<RoomResponseDto>> fetchAvailableRooms(String schoolName, String hostelName) {

        List<RoomResponseDto> rooms = roomRepository.findAll().stream().filter(
                room -> room.getRoomType().getHostelName().equals(hostelName) &&
                        room.getRoomType().getSchoolName().equals(schoolName) &&
                        room.getRoomStatus().equals(RoomStatus.AVAILABLE)
        ).map(
                room -> RoomResponseDto.builder()
                        .hostelName(room.getRoomType().getHostelName())
                        .schoolName(room.getRoomType().getSchoolName())
                        .uniqueCode(room.getRoomType().getUniqueCode())
                        .hostelContactMail(hostelContactMail(schoolName, hostelName))
                        .roomStatus(room.getRoomStatus().name())
                        .pricePerBed(room.getRoomType().getPricePerBed())
                        .description(room.getRoomType().getDescription())
                        .roomNumber(room.getRoomNumber())
                        .sex(String.valueOf(room.getSex()))
                        .numberInARoom(room.getRoomType().getNumberInARoom())
                        .bedAvailable(room.getBedAvailable())
                        .build()
        ).toList();

        return !rooms.isEmpty() ? ResponseEntity.ok().body(rooms) : ResponseEntity.noContent().build();

    }

    private String hostelContactMail(String schoolName, String hostelName) {

        return hostelPropertyRepository.findAll().stream().filter(
                hostelProperty -> hostelProperty.getHostelName().equalsIgnoreCase(hostelName) &&
                                    hostelProperty.getSchoolName().equals(School.valueOf(schoolName))
        ).findFirst().get().getContactEmail();

    }

}
