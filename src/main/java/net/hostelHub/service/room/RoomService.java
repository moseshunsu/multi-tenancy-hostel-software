package net.hostelHub.service.room;

import net.hostelHub.dto.Response;
import net.hostelHub.dto.room.RoomRequest;
import net.hostelHub.dto.room.RoomResponseDto;
import net.hostelHub.dto.room.RoomTypeRequest;
import net.hostelHub.entity.properties.HostelProperty;
import net.hostelHub.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface RoomService {

    ResponseEntity<Response> addRoomType(RoomTypeRequest roomTypeRequest);
    ResponseEntity<Response> addRoom(RoomRequest roomRequest) throws UserNotFoundException;
    ResponseEntity<RoomResponseDto> fetchRoomDetails(String schoolName, String hostelName, String roomNumber);
    ResponseEntity<List<RoomResponseDto>> fetchAvailableRooms(String schoolName, String hostelName);

}
