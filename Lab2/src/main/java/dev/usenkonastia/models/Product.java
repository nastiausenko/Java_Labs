package dev.usenkonastia.models;

import dev.usenkonastia.validation.annotations.MinValue;

public class Product {
    private String name;

    @MinValue(0)
    private Double price;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}