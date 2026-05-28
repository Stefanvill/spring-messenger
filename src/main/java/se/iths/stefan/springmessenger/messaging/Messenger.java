package se.iths.stefan.springmessenger.messaging;

public interface Messenger {
    void send(String message, String recipient);

}
