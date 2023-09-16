package net.hostelHub.dto.room;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RoomRequest {

    @NotNull(message = "room number cannot be be null")
    @Pattern(regexp = "^.{2,}$", message = "hostel name must be at least 1 character")
    private String roomNumber;

    @NotNull(message = "name of hostel cannot be be null")
    private String hostelName;

    @NotNull(message = "number in a room must not be null")
    @Min(1)
    private Integer numberInARoom;

    @NotNull(message = "room number cannot be be null")
    private String schoolName;

    @NotNull(message = "sex must be be null")
    private String sex;

    @NotNull(message = "room status must not be null")
    private String roomStatus;

    @NotNull(message = "unique code must not be null")
    private String uniqueCode;

}
