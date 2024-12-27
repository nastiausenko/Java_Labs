package dev.usenkonastia;

import dev.usenkonastia.models.*;

import java.util.List;

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
        User user = User.builder().username("Alice").age(19).password("12345").build();
        User user2 = User.builder().username("John").age(23).password("h23jbg").build();

        Product product = Product.builder().name("Laptop").price(1500.0).build();

        Order order = Order.builder().orderId("ORD12345").status("Shipped").build();

        OrderSQLGenerator orderSQLGenerator = new OrderSQLGenerator();
        System.out.println(orderSQLGenerator.generateCreateTableSQL());
        System.out.println();
        System.out.println(orderSQLGenerator.generateInsertSQL(List.of(order)));
        System.out.println();

        UserSQLGenerator userSQLGenerator = new UserSQLGenerator();
        System.out.println(userSQLGenerator.generateCreateTableSQL());
        System.out.println();
        System.out.println(userSQLGenerator.generateInsertSQL(List.of(user, user2)));
        System.out.println();

        ProductSQLGenerator productSQLGenerator = new ProductSQLGenerator();
        System.out.println(productSQLGenerator.generateCreateTableSQL());
        System.out.println();
        System.out.println(productSQLGenerator.generateInsertSQL(List.of(product)));
        System.out.println();
    }
}
