package net.hostelHub.service.token;

import net.hostelHub.entity.User;
import net.hostelHub.entity.token.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenService {
    void createPasswordResetTokenForUser(User user, String passwordToken);

    String validatePasswordResetToken(String passwordResetToken);

    Optional<User> findUserByPasswordToken(String passwordResetToken);

    PasswordResetToken findPasswordResetToken(String token);
}
