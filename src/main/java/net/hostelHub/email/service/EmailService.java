package net.hostelHub.email.service;

import net.hostelHub.email.dto.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);
    void sendMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);

}
