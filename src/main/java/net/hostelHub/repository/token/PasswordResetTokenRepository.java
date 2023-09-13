package net.hostelHub.repository.token;

import net.hostelHub.entity.token.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String passwordResetToken);
}