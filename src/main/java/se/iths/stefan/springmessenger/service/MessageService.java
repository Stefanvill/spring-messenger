package se.iths.stefan.springmessenger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.iths.stefan.springmessenger.messaging.Messenger;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final Messenger messenger;

    public void send(String message, String recipient) {


        messenger.send(message, recipient);
    }
}