package net.hostelHub.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.room.RoomRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import net.hostelHub.dto.room.RoomTypeRequest;
import net.hostelHub.exception.UserNotFoundException;
import net.hostelHub.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/room-types")
    public ResponseEntity<Response> addRoomType(@RequestBody @Valid RoomTypeRequest roomTypeRequest) {
        return roomService.addRoomType(roomTypeRequest);
    }

    @PostMapping("/room")
    public ResponseEntity<Response> addRoom(@RequestBody @Valid RoomRequest roomRequest) throws UserNotFoundException {
        return roomService.addRoom(roomRequest);
    }

    @GetMapping("/room")
    public ResponseEntity<RoomResponseDto> fetchRoomDetails(@RequestParam String schoolName,
                                                            @RequestParam String hostelName,
                                                            @RequestParam String roomNumber) {
        return roomService.fetchRoomDetails(schoolName, hostelName, roomNumber);
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> fetchAvailableRooms(@RequestParam String schoolName,
                                                                     @RequestParam String hostelName) {
        return roomService.fetchAvailableRooms(schoolName, hostelName);
    }
}
