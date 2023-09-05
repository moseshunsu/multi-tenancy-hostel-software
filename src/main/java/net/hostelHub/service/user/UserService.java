package net.hostelHub.service.user;

import net.hostelHub.dto.Response;
import net.hostelHub.dto.UserRequest;
import net.hostelHub.entity.User;
import net.hostelHub.utils.Role;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<Response> registerUser(UserRequest userRequest, Role role, Integer lengthOfCode);
    User fetchUser(String emailOrUsername);

}
