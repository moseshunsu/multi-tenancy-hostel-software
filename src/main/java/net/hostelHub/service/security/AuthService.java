package net.hostelHub.service.security;

import net.hostelHub.dto.AuthResponse;
import net.hostelHub.dto.LoginDto;

public interface AuthService {
    AuthResponse login(LoginDto loginDto);
}
