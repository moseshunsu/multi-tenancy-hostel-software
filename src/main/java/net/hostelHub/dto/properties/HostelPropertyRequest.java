package net.hostelHub.dto.properties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HostelPropertyRequest {

    @NotNull(message = "hostel name must not be be null")
    @Pattern(regexp = "^.{2,}$", message = "hostel name must be more than 2 characters")
    private String hostelName;

    @NotNull(message = "school name must not be be null")
    @Pattern(regexp = "^.{2,}$", message = "school name must be more than 2 characters")
    private String schoolName;

    @NotNull(message = "state must not be be null")
    @Pattern(regexp = "^.{2,}$", message = "state must be more than 2 characters")
    private String state;

    @Pattern(regexp = "^.{2,}$", message = "address must be more than 2 characters")
    private String address;

    @Pattern(regexp = "^.{2,}$", message = "description must be more than 2 characters")
    private String description;

    @Min(value = 1, message = "total rooms must be greater than 1")
    private Integer totalRooms;

    @Email
    private String contactEmail;

    @Pattern(regexp = "^\\d{10,}$", message = "contact number must not be less than 10 digits")
    private String contactPhone;

    @NotNull(message = "unique code must not be null")
    private String uniqueCode;

    @NotNull(message = "type must not be null")
    private String type;
}
