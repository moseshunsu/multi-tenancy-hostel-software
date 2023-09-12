package net.hostelHub.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hostelHub.email.dto.EmailDetails;
import net.hostelHub.email.service.EmailService;
import net.hostelHub.entity.User;
import net.hostelHub.event.RegistrationCompleteEvent;
import net.hostelHub.service.token.VerificationTokenService;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final VerificationTokenService tokenService;
    private final JavaMailSender mailSender;
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // 1. Get the newly registered user
        User user = event.getUser();

        // 2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();

        // 3. Save the verification token for the user
        tokenService.saveUserVerificationToken(user, verificationToken);

        // 4. Build the verification url to be sent to the user
        String url = event.getApplicationUrl() + "/api/v1/verify/email?token=" + verificationToken;

        // 5. Send the email
        String mailContent = "<p> Hi, " + user.getName() + ", </p>" +
                "<p>Thank you for registering with us, " +
                "Please, follow the link below to complete your registration.</p>" +
                "<a href=\"" + url + "\">Verify your email to activate your account</a>" +
                "<p> Thank you <br> Users Registration Portal Service";

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .messageBody(mailContent)
                .subject("Email Verification")
                .build();

        emailService.sendMail(emailDetails);
        log.info("Click the link to verify your registration: {}", url);
    }
}
