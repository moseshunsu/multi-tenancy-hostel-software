package net.hostelHub.service.user;

import jakarta.servlet.http.HttpServletRequest;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.UserRequest;
import net.hostelHub.entity.User;
import net.hostelHub.utils.Role;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {

    ResponseEntity<Response> registerUser(
            UserRequest userRequest,
            Role role,
            Integer lengthOfCode,
            final HttpServletRequest request
    );

    User fetchUser(String emailOrUsername);
    void changePassword(User user, String newPassword);
    Optional<User> findByEmail(String email);
}
