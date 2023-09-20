package net.hostelHub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
@Tag(
        name = "User Controller REST APIs/Endpoint",
        description = "This controller includes endpoints which allow users registration, resetting and changing of " +
                "password"
)
public class UserController {
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final RegistrationCompleteEventListener eventListener;

    @Operation(
            summary = "This endpoint allows registration of students or occupants",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "User Code Exists",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/sign-up")
    public ResponseEntity<Response> registerOccupant(@RequestBody @Valid UserRequest userRequest,
                                                     Role role,
                                                     Integer lengthOfCode,
                                                     HttpServletRequest request) {
        return userService.registerUser(userRequest, Role.OCCUPANT, ResponseUtils.LENGTH_OF_OCCUPANT_CODE, request);
    }

    @Operation(
            summary = "This endpoint allows registration of hostel owners or managers",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "User Code Exists",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("/sign-up/managers")
    public ResponseEntity<Response> registerManager(@RequestBody @Valid UserRequest userRequest,
                                                   Role role,
                                                   Integer lengthOfCode,
                                                   HttpServletRequest request) {
        return userService.registerUser(userRequest, Role.MANAGER, ResponseUtils.LENGTH_OF_TENANT_CODE, request);
    }

    @Operation(
            summary = "This endpoint allows users fetches their details",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/{emailOrUsername}")
    public User fetchUser(@PathVariable String emailOrUsername) {
        return userService.fetchUser(emailOrUsername);
    }

    @Operation(
            summary = "This endpoint allows users make request for password reset",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
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

    private String passwordResetEmailLink(User user,
                                          String applicationUrl,
                                          String passwordToken)
            throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/v1/users/reset-password?token="+passwordToken;
        eventListener.sendPasswordResetVerificationEmail(url);
        log.info("Click the link to reset your password :  {}", url);
        return url;
    }

    @Operation(
            summary = "This endpoint allows users to reset password their password after email verification",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
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

    @Operation(
            summary = "This endpoint allow authenticated users to change their password",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @PostMapping("/change-password")
    public String changePassword(@RequestBody PasswordRequestUtil requestUtil) {
        User user = userService.findByEmail(requestUtil.getEmail()).get();
        if (!userService.oldPasswordIsValid(user, requestUtil.getOldPassword())) {
            return "Incorrect old password";
        }
        userService.changePassword(user, requestUtil.getNewPassword());
        return "Password changed successfully";
    }

}
