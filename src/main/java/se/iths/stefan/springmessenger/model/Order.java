package se.iths.stefan.springmessenger.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// src/main/java/.../model/Message.java
@Getter
@Setter
public abstract class Order {
    private String customerName;
    private List<Product> OrderItems = new ArrayList<>();
    private double totalPrice;   // use double for money (or BigDecimal for production)

    public abstract String getType();
}
