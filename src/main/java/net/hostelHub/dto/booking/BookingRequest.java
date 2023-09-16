package net.hostelHub.dto.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingRequest {

    @NotNull(message = "name of hostel cannot be be null")
    private String hostelName;

    @NotNull(message = "name of school cannot be be null")
    private String schoolName;

    @NotNull(message = "unique code must not be null")
    private String uniqueCode;

    @NotNull(message = "academic year must not be null")
    private String academicYear;

    @NotNull(message = "room number must not be null")
    private String roomNumber;

    @NotNull(message = "occupant details must not be null")
    private String occupantEmail;

}
