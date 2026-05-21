package se.iths.stefan.springmessenger.messaging;

import se.iths.stefan.springmessenger.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface Messenger {
    void send(Message message);

}
