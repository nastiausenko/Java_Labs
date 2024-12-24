package dev.usenkonastia.validation;

/**
 * Represents a generic validator that performs validation of an object of a specific type.
 * This validator does not rely on reflection mechanisms to validate objects.
 *
 * @author Anastasiia Usenko
 *
 * @param <T> the type of object to be validated
 */
public interface ValidatorWithoutReflection<T> {

    /**
     * Validates the given object of type {@code T}.
     *
     * <p>This method should implement the logic necessary to verify that the object
     * satisfies the required constraints or business rules.</p>
     *
     * @param obj the object to be validated; must not be {@code null}
     */
    void validate(T obj);
}
