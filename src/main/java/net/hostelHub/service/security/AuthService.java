package net.hostelHub.service.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.hostelHub.dto.AuthResponse;
import net.hostelHub.dto.LoginDto;

import java.io.IOException;

public interface AuthService {
    AuthResponse login(LoginDto loginDto);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
