package dev.usenkonastia.models;

import dev.usenkonastia.validation.annotations.NotNull;

public class Order {
    @NotNull
    private String orderId;

    private String status;

    public Order(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }
}