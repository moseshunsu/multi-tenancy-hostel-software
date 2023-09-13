package net.hostelHub.service.token;

import net.hostelHub.dto.Response;
import net.hostelHub.entity.User;
import net.hostelHub.entity.token.VerificationToken;
import org.springframework.http.ResponseEntity;

public interface VerificationTokenService {
    void saveUserVerificationToken(User user, String token);
    String validateToken(String token);
    ResponseEntity<Response> verifyEmail(String token);
    VerificationToken generateNewVerificationToken(String oldToken);
}
