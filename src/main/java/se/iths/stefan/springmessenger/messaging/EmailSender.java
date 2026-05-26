package se.iths.stefan.springmessenger.messaging;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import se.iths.stefan.springmessenger.model.Email;
import se.iths.stefan.springmessenger.model.Order;
import se.iths.stefan.springmessenger.model.Product;

import java.nio.charset.StandardCharsets;

@Component("email")
@RequiredArgsConstructor
public class EmailSender implements Messenger {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Override

    public void send(Order message) {
        if (!(message instanceof Email email)) throw new IllegalArgumentException("Expected Email");
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

            helper.setFrom(from);
            helper.setTo(email.getCustomerName());

            StringBuilder body = new StringBuilder();
            body.append("Customer: ").append(email.getCustomerName()).append("\n\n");
            body.append("Items:\n");

            if (email.getOrderItems().isEmpty()) {
                body.append("(no items)\n");
            }

            helper.setText(body.toString());
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            // log or rethrow so you can see the real error
            throw new RuntimeException("Failed to send email", e);
        }
    }
}