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
import java.util.List;

import jakarta.mail.internet.InternetAddress;

@Component("email")
@RequiredArgsConstructor
public class EmailSender implements Messenger {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void send(Order message) {
        if (!(message instanceof Email email)) {
            throw new IllegalArgumentException("Expected Email message");
        }

        // validate recipient is an email address
        String recipient = email.getCustomerName(); // ideally rename to getRecipient/getCustomerEmail
        try {
            new InternetAddress(recipient).validate();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid recipient email: " + recipient, ex);
        }

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

            helper.setFrom(from);
            helper.setTo(recipient);

            StringBuilder body = new StringBuilder();
            body.append("Customer: ").append(recipient).append("\n\n");
            body.append("Items:\n");

            List<Product> products = email.getOrderItems();
            double computed = 0.0;
            if (products != null && !products.isEmpty()) {
                for (Product p : products) {
                    body.append("- ").append(p.getName())
                            .append(" : ").append(p.getPrice())
                            .append("\n");
                    computed += p.getPrice();
                }
            } else {
                body.append("(no items)\n");
            }

            double total = email.getTotalPrice();
            if (total == 0) total = computed;
            body.append("\nTotal: ").append(total).append(" kr\n");

            helper.setText(body.toString(), false);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            // log or rethrow with cause (consider using a logger)
            throw new RuntimeException("Failed to send email", e);
        }
    }
}