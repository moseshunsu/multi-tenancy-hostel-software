package net.hostelHub.service.token;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.hostelHub.dto.Data;
import net.hostelHub.dto.Response;
import net.hostelHub.entity.User;
import net.hostelHub.entity.token.VerificationToken;
import net.hostelHub.repository.UserRepository;
import net.hostelHub.repository.token.VerificationTokenRepository;
import net.hostelHub.utils.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

import static net.hostelHub.service.user.UserServiceImpl.applicationUrl;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl  implements VerificationTokenService{

    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final HttpServletRequest servletRequest;

    @Override
    public void saveUserVerificationToken(User user, String token) {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "Invalid verification token";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0)  {
//            tokenRepository.delete(verificationToken);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public ResponseEntity<Response> verifyEmail(String token) {

        String url = applicationUrl(servletRequest)+"/api/v1/verify/resend-verification-token?token="+token;

        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken.getUser().isEnabled()){
            return ResponseEntity.ok().body(
                    Response.builder()
                            .responseCode(ResponseUtils.SUCCESS_CODE)
                            .responseMessage(ResponseUtils.ACCOUNT_VERIFICATION_MESSAGE)
                            .data(Data.builder()
                                    .uniqueCode(verificationToken.getUser().getUniqueCode())
                                    .email(verificationToken.getUser().getEmail())
                                    .username(verificationToken.getUser().getUsername())
                                    .build())
                            .build()
            );
        }
        String verificationResult = this.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")) {
            return ResponseEntity.ok().body(
                    Response.builder()
                            .responseCode(ResponseUtils.SUCCESS_CODE)
                            .responseMessage(ResponseUtils.EMAIL_VERIFICATION_MESSAGE)
                            .data(Data.builder()
                                    .uniqueCode(verificationToken.getUser().getUniqueCode())
                                    .email(verificationToken.getUser().getEmail())
                                    .username(verificationToken.getUser().getUsername())
                                    .build())
                            .build()
            );
        }
        return ResponseEntity.ok().body(
                Response.builder()
                        .responseCode(ResponseUtils.INVALID_TOKEN_CODE)
                        .responseMessage("Invalid verification link, <a href=\"" + url + "\"> Get a new verification link. </a>")
                        .data(Data.builder()
                                .uniqueCode(verificationToken.getUser().getUniqueCode())
                                .email(verificationToken.getUser().getEmail())
                                .username(verificationToken.getUser().getUsername())
                                .build())
                        .build()
        );
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        var verificationTokenTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(verificationTokenTime.getTokenExpirationTime());
        return tokenRepository.save(verificationToken);
    }
}
