package se.iths.stefan.springmessenger.messaging;

import se.iths.stefan.springmessenger.model.Order;

public interface Messenger {
    void send(Order message);

}
