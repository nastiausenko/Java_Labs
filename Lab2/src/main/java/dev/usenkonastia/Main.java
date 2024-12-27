package dev.usenkonastia;

import dev.usenkonastia.models.Order;
import dev.usenkonastia.models.OrderSQLGenerator;
import dev.usenkonastia.models.Product;
import dev.usenkonastia.models.User;
import dev.usenkonastia.processor.validation.Validator;
/**
 * Entry point of the application that demonstrates validation of objects
 * with and without the use of reflection.
 * <p>
 * The application compares the performance of reflection-based validation
 * against a manually implemented validation mechanism for {@link User} objects.
 * It also showcases validation of {@link Product} and {@link Order} using reflection.
 * </p>
 * <p>
 * Author: Anastasiia Usenko
 * </p>
 */
public class Main {
    public static void main(String[] args) {
        User user = new User("Alice", 19);
        Product product = new Product("Laptop", 1500.0);
        Order order = new Order("ORD12345", "Shipped");

        validateWithReflection(product);
        validateWithReflection(order);
        validateWithReflection(user);

        OrderSQLGenerator generator = new OrderSQLGenerator();
        System.out.println(generator.generateCreateTableSQL());
        System.out.println(generator.generateInsertSQL(user));
    }


    /**
     * Validates an object using a reflection-based validation mechanism.
     *
     * @param obj the object to validate
     */
    private static void validateWithReflection(Object obj) {
        try {
            Validator.validate(obj);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
