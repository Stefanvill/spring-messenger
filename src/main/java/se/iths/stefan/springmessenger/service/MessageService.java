package se.iths.stefan.springmessenger.service;

import org.springframework.stereotype.Service;
import se.iths.stefan.springmessenger.messaging.Messenger;
import se.iths.stefan.springmessenger.model.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    private final Map<String, Messenger> messengers;

    public MessageService(Map<String, Messenger> messengers) {
        this.messengers = messengers;
    }

    public void send(Message message) {
        Messenger messenger = messengers.get(message.getType());
        if (messenger == null) {
            throw new IllegalArgumentException("No messenger for type: " + message.getType());
        }
        messenger.send(message);
    }
}