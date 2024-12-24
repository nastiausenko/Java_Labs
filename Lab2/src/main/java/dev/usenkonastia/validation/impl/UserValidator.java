package dev.usenkonastia.validation.impl;

import dev.usenkonastia.models.User;
import dev.usenkonastia.validation.ValidatorWithoutReflection;

/**
 * Implementation of {@link ValidatorWithoutReflection} for validating {@link User} objects.
 * <p>
 * This class provides validation logic for {@link User} instances, ensuring that
 * their properties meet specific criteria.
 * </p>
 *
 * @author Anastasiia Usenko
 */
public class UserValidator implements ValidatorWithoutReflection<User> {

    /**
     * Validates the provided {@link User} object.
     * <p>
     * This method checks that the username is not null, has a length between 3 and 20,
     * and that the age is not null and is within the range of 18 to 100.
     * </p>
     *
     * @param user the {@link User} object to validate.
     * @throws IllegalArgumentException if the user properties do not meet the specified criteria.
     */
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
