package net.hostelHub.dto.properties;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HostelPropertyRequest {
    private String hostelName;
    private String schoolName;
    private String state;
    private String address;
    private String description;
    private int totalRooms;
    private String contactEmail;
    private String contactPhone;
    private String uniqueCode;
    private String type;
}
