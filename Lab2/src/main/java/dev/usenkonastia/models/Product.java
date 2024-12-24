package dev.usenkonastia.models;

import dev.usenkonastia.validation.annotations.MinValue;
import dev.usenkonastia.validation.annotations.NotNull;

/**
 * Represents a product with name and price, validated using custom annotations.
 *
 * @author Anastasiia Usenko
 */
public class Product {

    @NotNull
    private String name;

    @MinValue(0)
    private Double price;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}