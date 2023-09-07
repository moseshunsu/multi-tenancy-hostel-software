package net.hostelHub.dto.room;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoomResponseDto {
    private String hostelName;
    private String schoolName;
    private String uniqueCode;
    private String hostelContactMail;
    private Double pricePerBed;
    private String roomStatus;
    private String description;
    private String roomNumber;
    private String sex;
    private int numberInARoom;
    private int bedAvailable;
}
