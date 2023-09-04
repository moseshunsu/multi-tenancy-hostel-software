package net.hostelHub.controller;

import net.hostelHub.dto.Response;
import net.hostelHub.dto.UserRequest;
import net.hostelHub.entity.User;
import net.hostelHub.service.UserService;
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
                                                     Role role, Integer lengthOfCode) {
        return userService.registerUser(userRequest, Role.OCCUPANT, ResponseUtils.LENGTH_OF_OCCUPANT_CODE);
    }

    @PostMapping("/sign-up/tenants")
    public ResponseEntity<Response> registerTenant(@RequestBody UserRequest userRequest,
                                                                Role role, Integer lengthOfCode) {
        return userService.registerUser(userRequest, Role.TENANT, ResponseUtils.LENGTH_OF_TENANT_CODE);
    }

    @GetMapping("/{emailOrUsername}")
    public User fetchUser(@PathVariable String emailOrUsername) {
        return userService.fetchUser(emailOrUsername);
    }

}
