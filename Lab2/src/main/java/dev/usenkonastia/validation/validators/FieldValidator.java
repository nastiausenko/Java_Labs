package dev.usenkonastia.validation.validators;

import java.lang.reflect.Field;

/**
 * Interface representing a validator for fields within an object.
 * <p>
 * Implementations of this interface are responsible for validating individual fields of an object
 * according to specific validation rules.
 * </p>
 *
 * @author Anastasiia Usenko
 */
public interface FieldValidator {

    /**
     * Validates a specific field of the provided object.
     *
     * @param obj   the object containing the field to be validated
     * @param field the field to validate
     * @throws IllegalArgumentException if the field's value is invalid
     * @throws IllegalAccessException   if the field is not accessible
     */
    void validate(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException;
}
