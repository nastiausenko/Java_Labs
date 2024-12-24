package dev.usenkonastia.validation;

import dev.usenkonastia.models.User;

public class UserValidator implements ValidatorWithoutReflection<User> {

    @Override
    public void validate(User user) {
        if (user.getUsername() == null) {
            throw new IllegalArgumentException("username cannot be null.");
        }
        if (user.getUsername().length() < 3 || user.getUsername().length() > 20) {
            throw new IllegalArgumentException("username length must be between 3 and 20.");
        }
        if (user.getAge() == null) {
            throw new IllegalArgumentException("age cannot be null.");
        }
        if (user.getAge() < 18 || user.getAge() > 100) {
            throw new IllegalArgumentException("age must be between 18 and 100.");
        }
    }
}
