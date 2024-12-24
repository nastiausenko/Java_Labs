package dev.usenkonastia.models;

import dev.usenkonastia.validation.annotations.NotNull;
import dev.usenkonastia.validation.annotations.StringLength;

public class Order {
    @NotNull
    private String orderId;

    @StringLength(max = 10)
    private String status;

    public Order(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }
}