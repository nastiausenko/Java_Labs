package dev.usenkonastia.models;

import dev.usenkonastia.processor.Column;
import dev.usenkonastia.processor.SqlGenerator;
import dev.usenkonastia.processor.Table;
import dev.usenkonastia.processor.validation.annotations.MinValue;
import dev.usenkonastia.processor.validation.annotations.NotNull;

/**
 * Represents a product with name and price, validated using custom annotations.
 *
 * @author Anastasiia Usenko
 */
@SqlGenerator
@Table(name="products")
public class Product {
    @Column(name="product_name", type = "TEXT")
    @NotNull
    private String name;

    @Column(name = "price", type = "DECIMAL")
    @MinValue(0)
    private Double price;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}