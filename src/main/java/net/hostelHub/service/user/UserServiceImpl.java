package net.hostelHub.service.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.hostelHub.dto.Data;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.UserRequest;
import net.hostelHub.entity.User;
import net.hostelHub.event.RegistrationCompleteEvent;
import net.hostelHub.repository.UserRepository;
import net.hostelHub.utils.ResponseUtils;
import net.hostelHub.utils.Role;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;


    @Override
    public ResponseEntity<Response> registerUser(UserRequest userRequest, Role role, Integer lengthOfCode,
                                                     final HttpServletRequest request) {

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
        publisher.publishEvent(new RegistrationCompleteEvent(saveduser, applicationUrl(request)));

        return successfulResponse(saveduser);
    }

    @Override
    public User fetchUser(String emailOrUsername) {
        return userRepository.existsByUsernameOrEmail(emailOrUsername, emailOrUsername)
                ? userRepository.findByUsernameOrEmail(emailOrUsername, emailOrUsername).get()
                : null;
    }

    public static String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private ResponseEntity<Response> successfulResponse(User user) {
        Data userData = Data.builder()
                .uniqueCode(user.getUniqueCode())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        return ResponseEntity.ok(Response.builder()
                .responseCode(ResponseUtils.SUCCESS_CODE)
                .responseMessage(ResponseUtils.USER_REGISTRATION_MESSAGE)
                .data(userData)
                .build());
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
