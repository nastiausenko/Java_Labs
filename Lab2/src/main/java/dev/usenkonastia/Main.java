package dev.usenkonastia;

import dev.usenkonastia.models.Order;
import dev.usenkonastia.models.Product;
import dev.usenkonastia.models.User;
import dev.usenkonastia.validation.UserValidator;
import dev.usenkonastia.validation.Validator;
import dev.usenkonastia.validation.ValidatorWithoutReflection;

public class Main {
    public static void main(String[] args) {
        User user = new User("Alice", 19);
        Product product = new Product("Laptop", 1500.0);
        Order order = new Order("ORD12345", "Shipped");

        validateWithReflection(product);
        validateWithReflection(order);

        long startReflection = System.nanoTime();
        validateWithReflection(user);
        long endReflection = System.nanoTime();

        long startNonReflection = System.nanoTime();
        validateWithoutReflection(user);
        long endNonReflection = System.nanoTime();

        System.out.println("Validation with reflection took: " + (endReflection - startReflection) + " ns");
        System.out.println("Validation without reflection took: " + (endNonReflection - startNonReflection) + " ns");
    }

    private static void validateWithReflection(Object obj) {
        try {
            Validator.validate(obj);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void validateWithoutReflection(User user) {
        ValidatorWithoutReflection<User> userValidator = new UserValidator();
        try {
            userValidator.validate(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
