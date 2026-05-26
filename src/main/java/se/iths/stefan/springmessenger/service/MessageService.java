package se.iths.stefan.springmessenger.service;

import org.springframework.stereotype.Service;
import se.iths.stefan.springmessenger.messaging.Messenger;
import se.iths.stefan.springmessenger.model.Order;

import java.util.Map;

@Service
public class MessageService {
    private final Map<String, Messenger> messengers;

    public MessageService(Map<String, Messenger> messengers) {
        this.messengers = messengers;
    }

    public void send(Order order) {
        Messenger messenger = messengers.get(order.getType());
        if (messenger == null) {
            throw new IllegalArgumentException("No messenger for type: " + order.getType());
        }
        messenger.send(order);
    }
}