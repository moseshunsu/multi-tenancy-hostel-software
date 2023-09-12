package net.hostelHub.controller;

import jakarta.servlet.http.HttpServletRequest;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.UserRequest;
import net.hostelHub.entity.User;
import net.hostelHub.service.user.UserService;
import net.hostelHub.utils.ResponseUtils;
import net.hostelHub.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<Response> registerOccupant(@RequestBody UserRequest userRequest,
                                                     Role role,
                                                     Integer lengthOfCode,
                                                     HttpServletRequest request) {
        return userService.registerUser(userRequest, Role.OCCUPANT, ResponseUtils.LENGTH_OF_OCCUPANT_CODE, request);
    }

    @PostMapping("/sign-up/managers")
    public ResponseEntity<Response> registerManager(@RequestBody UserRequest userRequest,
                                                   Role role,
                                                   Integer lengthOfCode,
                                                   HttpServletRequest request) {
        return userService.registerUser(userRequest, Role.MANAGER, ResponseUtils.LENGTH_OF_TENANT_CODE, request);
    }

    @GetMapping("/{emailOrUsername}")
    public User fetchUser(@PathVariable String emailOrUsername) {
        return userService.fetchUser(emailOrUsername);
    }

}
