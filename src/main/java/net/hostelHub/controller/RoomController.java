package net.hostelHub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.room.RoomRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import net.hostelHub.dto.room.RoomTypeRequest;
import net.hostelHub.exception.UserNotFoundException;
import net.hostelHub.service.room.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Room Controller REST APIs/Endpoint",
        description = "This controller includes endpoints which allow hostel managers create and manage their " +
                "property rooms"
)
public class RoomController {
    private final RoomService roomService;

    @Operation(
            summary = "This endpoint allows property owners to add room types",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Property Exists",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Hostel not found",
                            responseCode = "500"
                    )
            }
    )
    @PostMapping("/room-types")
    public ResponseEntity<Response> addRoomType(@RequestBody @Valid RoomTypeRequest roomTypeRequest) {
        return roomService.addRoomType(roomTypeRequest);
    }

    @Operation(
            summary = "This endpoint allows property owners to add room",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Room Exists",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "User or Room type not found",
                            responseCode = "500"
                    )
            }
    )
    @PostMapping("/room")
    public ResponseEntity<Response> addRoom(@RequestBody @Valid RoomRequest roomRequest) throws UserNotFoundException {
        return roomService.addRoom(roomRequest);
    }

    @Operation(
            summary = "This endpoint allows users fetch room details of a particular hostel",
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
    @GetMapping("/room-details")
    public ResponseEntity<RoomResponseDto> fetchRoomDetails(@RequestParam String schoolName,
                                                            @RequestParam String hostelName,
                                                            @RequestParam String roomNumber) {
        return roomService.fetchRoomDetails(schoolName, hostelName, roomNumber);
    }

    @Operation(
            summary = "This endpoint allows occupants search for available rooms in a particular school",
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
    public ResponseEntity<List<RoomResponseDto>> fetchAvailableRooms(@RequestParam String schoolName,
                                                                     @RequestParam String hostelName) {
        return roomService.fetchAvailableRooms(schoolName, hostelName);
    }
}
