package net.hostelHub.dto.booking;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingRequest {
    private String hostelName;
    private String schoolName;
    private String uniqueCode;
    private String academicYear;
    private String roomNumber;
    private String occupantEmail;
}
