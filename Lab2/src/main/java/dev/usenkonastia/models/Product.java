package dev.usenkonastia.models;

import dev.usenkonastia.processor.validation.annotations.*;
import lombok.Builder;
import lombok.Getter;

/**
 * Represents a product with name and price, validated using custom annotations.
 *
 * @author Anastasiia Usenko
 */
@SqlGenerator
@Getter
@Builder
@Table(name="products")
public class Product {
    @Column(name="product_name", type = "TEXT")
    @StringLength(min = 1, max = 255)
    @NotNull
    private String name;

    @Column(name = "price", type = "DECIMAL")
    @MinValue(0)
    private Double price;
}