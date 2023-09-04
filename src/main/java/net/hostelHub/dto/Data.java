package net.hostelHub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {

    private String uniqueCode;
    private String email;
    private String username;

}
