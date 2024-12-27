package dev.usenkonastia;

import dev.usenkonastia.models.*;

import java.util.List;

/**
 * Entry point of the application that demonstrates the generation of SQL statements for various entities.
 * <p>
 * The application creates instances of User, Product, and Order, and prints out SQL statements
 * for creating and inserting data into the respective tables.
 * </p>
 *
 * @author Anastasiia Usenko
 */
public class Main {
    public static void main(String[] args) {
        User user1 = User.builder().username("Alice").age(19).password("12345").build();
        User user2 = User.builder().username("John").age(23).password("h23jbg").build();

        Product product = Product.builder().name("Laptop").price(1500.0).build();

        Order order = Order.builder().orderId("ORD12345").status("Shipped").build();

        OrderSQLGenerator orderSQLGenerator = new OrderSQLGenerator();
        printSQL("Order", orderSQLGenerator.generateCreateTableSQL(),
                orderSQLGenerator.generateInsertSQL(List.of(order)));

        UserSQLGenerator userSQLGenerator = new UserSQLGenerator();
        printSQL("User", userSQLGenerator.generateCreateTableSQL(),
                userSQLGenerator.generateInsertSQL(List.of(user1, user2)));

        ProductSQLGenerator productSQLGenerator = new ProductSQLGenerator();
        printSQL("Product", productSQLGenerator.generateCreateTableSQL(),
                productSQLGenerator.generateInsertSQL(List.of(product)));
    }

    /**
     * Utility method to print SQL statements to the console.
     *
     * @param entityName   the name of the entity (e.g., "User", "Product", "Order")
     * @param createTableSQL the SQL statement for creating the table
     * @param insertSQL      the SQL statement for inserting data
     */
    private static void printSQL(String entityName, String createTableSQL, String insertSQL) {
        System.out.println("-------- " + entityName + " SQL --------");
        System.out.println(createTableSQL);
        System.out.println();
        System.out.println(insertSQL);
        System.out.println();
    }
}
