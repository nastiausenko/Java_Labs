package dev.usenkonastia;

import dev.usenkonastia.models.User;
import dev.usenkonastia.validation.Validator;

public class Main {
    public static void main(String[] args) {
        User user = new User("Alice", 19);

        try {
            Validator.validate(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}