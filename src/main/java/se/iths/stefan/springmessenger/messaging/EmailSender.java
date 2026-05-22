package se.iths.stefan.springmessenger.messaging;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import se.iths.stefan.springmessenger.model.Email;
import se.iths.stefan.springmessenger.model.Message;
import se.iths.stefan.springmessenger.model.Product;

import java.nio.charset.StandardCharsets;

@Component("email")
@RequiredArgsConstructor
public class EmailSender implements Messenger {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void send(Message message) {
        if (!(message instanceof Email email)) throw new IllegalArgumentException("Expected Email");
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

            helper.setFrom(from);
            helper.setTo(email.getCustomerName());

            StringBuilder body = new StringBuilder();
            body.append("Order date: ").append(email.getOrderDate()).append("\n");
            body.append("Customer: ").append(email.getCustomerName()).append("\n\n");
            body.append("Items:\n");
            double computed = 0;
            for (Product p : email.getProducts()) {
                body.append("- ").append(p.getName()).append(" : ").append(p.getPrice()).append("\n");
                computed += p.getPrice();
            }
            if (email.getProducts().isEmpty()) {
                body.append("(no items)\n");
            }
            double total = email.getTotalPrice();
            if (total == 0) total = computed;
            body.append("\nTotal: ").append(total).append(" kr\n");

            helper.setText(body.toString());
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            // log or rethrow so you can see the real error
            throw new RuntimeException("Failed to send email", e);
        }
    }
}