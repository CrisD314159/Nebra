package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import com.crisdevApps.Nebra.exceptions.UnexpectedException;
import com.crisdevApps.Nebra.services.interfaces.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {

    @Value("${front.url}")
    private String frontUrl;
    private final JavaMailSender javaMailSender;

    @Override
    public void SendEmail(EmailDTO emailDTO, String template, boolean verification) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("team@nebra.com");
            mimeMessageHelper.setTo(emailDTO.to());
            mimeMessageHelper.setSubject(emailDTO.subject());

            String htmlTemplate = readHtmlTemplate(template);

            String frontPathOrCode = verification ? emailDTO.frontPathOrCode() : String.format("%s/%s", frontUrl, emailDTO.frontPathOrCode());
            htmlTemplate = htmlTemplate.replace("{{name}}", emailDTO.receiverName());
            htmlTemplate = htmlTemplate.replace("{{message}}", emailDTO.message());
            htmlTemplate = htmlTemplate.replace("{{button}}", emailDTO.buttonName());
            htmlTemplate = htmlTemplate.replace("{{code}}", frontPathOrCode);

            mimeMessageHelper.setText(htmlTemplate, true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException | IOException e) {
            throw new UnexpectedException(e.getMessage());
        }
    }


    private String readHtmlTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        byte[] bytes = resource.getInputStream().readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
