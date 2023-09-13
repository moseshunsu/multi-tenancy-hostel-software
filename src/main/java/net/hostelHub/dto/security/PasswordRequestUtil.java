package net.hostelHub.dto.security;

import lombok.Data;

@Data
public class PasswordRequestUtil {
    private String email;
    private String oldPassword;
    private String newPassword;
}