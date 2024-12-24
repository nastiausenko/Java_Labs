package dev.usenkonastia;

import dev.usenkonastia.models.Order;
import dev.usenkonastia.models.Product;
import dev.usenkonastia.models.User;
import dev.usenkonastia.validation.Validator;

public class Main {
    public static void main(String[] args) {
        User user = new User("Alice", 19);
        Product product = new Product("Laptop", 1500.0);
        Order order = new Order("ORD12345", "Shipped");

        try {
            Validator.validate(user);
            Validator.validate(product);
            Validator.validate(order);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}