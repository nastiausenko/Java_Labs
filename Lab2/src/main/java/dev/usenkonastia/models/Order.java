package dev.usenkonastia.models;

import dev.usenkonastia.processor.Column;
import dev.usenkonastia.processor.SqlGenerator;
import dev.usenkonastia.processor.Table;
import dev.usenkonastia.processor.validation.annotations.NotNull;
import dev.usenkonastia.processor.validation.annotations.StringLength;

/**
 * Represents an order with orderId and status, validated using custom annotations.
 *
 * @author Anastasiia Usenko
 */
@SqlGenerator
@Table(name = "orders")
public class Order {

    @Column(name = "orderId", type = "TEXT")
    @NotNull
    private String orderId;

    @Column(name = "status", type = "TEXT")
    @StringLength(max = 10)
    private String status;

    public Order(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }
}