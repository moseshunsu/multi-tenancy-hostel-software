package net.hostelHub.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;

}
