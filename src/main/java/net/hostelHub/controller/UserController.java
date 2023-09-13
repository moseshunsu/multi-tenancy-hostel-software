package net.hostelHub.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hostelHub.dto.Response;
import net.hostelHub.dto.UserRequest;
import net.hostelHub.dto.security.PasswordRequestUtil;
import net.hostelHub.entity.User;
import net.hostelHub.event.listener.RegistrationCompleteEventListener;
import net.hostelHub.service.token.PasswordResetTokenService;
import net.hostelHub.service.user.UserService;
import net.hostelHub.utils.ResponseUtils;
import net.hostelHub.utils.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

import static net.hostelHub.service.user.UserServiceImpl.applicationUrl;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final RegistrationCompleteEventListener eventListener;

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

    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                       final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {

        Optional<User> user = userService.findByEmail(passwordRequestUtil.getEmail());
        String passwordResetUrl = "";
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            passwordResetTokenService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(servletRequest), passwordResetToken);
        }
        return passwordResetUrl;
    }

    private String passwordResetEmailLink(User user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/v1/users/reset-password?token="+passwordToken;
        eventListener.sendPasswordResetVerificationEmail(url);
        log.info("Click the link to reset your password :  {}", url);
        return url;
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                @RequestParam("token") String token){
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(token);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "Invalid token password reset token";
        }
        Optional<User> user = Optional.ofNullable(passwordResetTokenService.findUserByPasswordToken(token)).get();
        if (user.isPresent()) {
            userService.changePassword(user.get(), passwordRequestUtil.getNewPassword());
            return "Password has been reset successfully";
        }
        return "Invalid password reset token";
    }

}
