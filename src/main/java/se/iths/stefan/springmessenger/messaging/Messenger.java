package se.iths.stefan.springmessenger.messaging;

import se.iths.stefan.springmessenger.model.Message;

public interface Messenger {
    void send(Message message);
}
