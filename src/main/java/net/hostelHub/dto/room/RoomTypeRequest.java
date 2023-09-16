package net.hostelHub.dto.room;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomTypeRequest {

    @NotNull(message = "number of people in a room cannot be be null")
    @Min(1)
    private Integer numberInARoom;

    @NotNull(message = "name of hostel cannot be be null")
    private String hostelName;

    @NotNull(message = "name of school cannot be be null")
    private String schoolName;

    private Double pricePerBed;
    private String description;

    @NotNull(message = "unique code must not be null")
    private String uniqueCode;
}
