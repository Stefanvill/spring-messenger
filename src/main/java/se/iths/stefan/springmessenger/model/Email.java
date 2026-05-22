package se.iths.stefan.springmessenger.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Email extends Message {

    @Override
    public String getType() {
        return "email";
    }
}
