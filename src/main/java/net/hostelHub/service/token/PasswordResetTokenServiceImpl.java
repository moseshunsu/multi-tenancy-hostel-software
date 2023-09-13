package net.hostelHub.service.token;

import lombok.RequiredArgsConstructor;
import net.hostelHub.entity.User;
import net.hostelHub.entity.token.PasswordResetToken;
import net.hostelHub.repository.token.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        PasswordResetToken passwordRestToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepository.save(passwordRestToken);
    }

    @Override
    public String validatePasswordResetToken(String passwordResetToken) {
        PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(passwordResetToken);
        if(passwordToken == null){
            return "Invalid verification token";
        }
        User user = passwordToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((passwordToken.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "Link already expired, resend link";
        }
        return "valid";
    }

    @Override
    public Optional<User> findUserByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
    }

    @Override
    public PasswordResetToken findPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }
}