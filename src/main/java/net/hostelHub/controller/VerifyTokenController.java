package net.hostelHub.controller;

import lombok.RequiredArgsConstructor;
import net.hostelHub.dto.Response;
import net.hostelHub.service.token.VerificationTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/verify")
public class VerifyTokenController{

    private final VerificationTokenService tokenService;

    @GetMapping("/email")
    public ResponseEntity<Response> verifyEmail(String token) {
        return tokenService.verifyEmail(token);
    }

}
