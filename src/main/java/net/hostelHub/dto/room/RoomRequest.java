package net.hostelHub.dto.room;

import lombok.Data;

@Data
public class RoomRequest {

    private String roomNumber;
    private String hostelName;
    private int numberInARoom;
    private String schoolName;
    private String sex;
    private String roomStatus;
    private String uniqueCode;

}
