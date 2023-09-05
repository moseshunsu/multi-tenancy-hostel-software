package net.hostelHub.dto.room;

import lombok.Data;

@Data
public class RoomTypeRequest {
    private Integer numberInARoom;
    private String hostelName;
    private String schoolName;
    private Double pricePerBed;
    private String description;
    private String uniqueCode;
}
