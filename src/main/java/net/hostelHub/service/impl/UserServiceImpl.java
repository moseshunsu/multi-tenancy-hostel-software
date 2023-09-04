package net.hostelHub.service.impl;

import net.hostelHub.dto.Data;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.UserRequest;
import net.hostelHub.entity.User;
import net.hostelHub.repository.UserRepository;
import net.hostelHub.service.UserService;
import net.hostelHub.utils.ResponseUtils;
import net.hostelHub.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Response> registerUser(UserRequest userRequest, Role role, Integer lengthOfCode) {

        boolean userExists = userRepository.findAll().stream()
                .anyMatch(user -> userRequest.getEmail().equalsIgnoreCase(user.getEmail()) ||
                                  userRequest.getUsername().equalsIgnoreCase(user.getUsername())
                );

        if (userExists) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .responseCode(ResponseUtils.USER_EXISTS_CODE)
                    .responseMessage(ResponseUtils.USER_EXISTS_MESSAGE)
                    .build());
        }

        User user = User.builder()
                .uniqueCode(ResponseUtils.generateClientCode(lengthOfCode))
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(role)
                .build();

        User saveduser = userRepository.save(user);

        return successfulResponse(saveduser);
    }


    @Override
    public User fetchUser(String emailOrUsername) {
        return userRepository.existsByUsernameOrEmail(emailOrUsername, emailOrUsername)
                ? userRepository.findByUsernameOrEmail(emailOrUsername, emailOrUsername).get()
                : null;
    }

    private ResponseEntity<Response> successfulResponse(User user) {
        Data userData = Data.builder()
                .uniqueCode(user.getUniqueCode())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        return ResponseEntity.ok(Response.builder()
                .responseCode(ResponseUtils.SUCCESS_CODE)
                .responseMessage(ResponseUtils.FETCHED_MESSAGE)
                .data(userData)
                .build());
    }

}
