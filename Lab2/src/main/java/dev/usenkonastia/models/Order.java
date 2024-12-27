package dev.usenkonastia.models;

import dev.usenkonastia.processor.validation.annotations.Column;
import dev.usenkonastia.processor.validation.annotations.SqlGenerator;
import dev.usenkonastia.processor.validation.annotations.Table;
import dev.usenkonastia.processor.validation.annotations.NotNull;
import dev.usenkonastia.processor.validation.annotations.StringLength;
import lombok.Builder;
import lombok.Getter;

/**
 * Represents an order with orderId and status, validated using custom annotations.
 *
 * @author Anastasiia Usenko
 */
@SqlGenerator
@Getter
@Builder
@Table(name = "orders")
public class Order {

    @Column(name = "orderId", type = "TEXT")
    @NotNull
    private String orderId;

    @Column(name = "status", type = "TEXT")
    @NotNull
    @StringLength(max = 10)
    private String status;
}