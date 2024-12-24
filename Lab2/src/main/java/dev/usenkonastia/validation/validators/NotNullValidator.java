package dev.usenkonastia.validation.validators;

import dev.usenkonastia.validation.annotations.NotNull;

import java.lang.reflect.Field;

/**
 * Validator for fields annotated with {@link NotNull}.
 * <p>
 * Ensures that fields are not null.
 * </p>
 *
 * @author Anastasiia Usenko
 */
public class NotNullValidator implements FieldValidator {

    /**
     * Validates that the specified field in the given object is not null.
     *
     * @param obj   the object containing the field to be validated
     * @param field the field to validate
     * @throws IllegalArgumentException if the field value is null
     * @throws IllegalAccessException   if the field cannot be accessed
     */
    @Override
    public void validate(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(obj);

        if (value == null) {
            throw new IllegalArgumentException(field.getName() + " cannot be null.");
        }
    }
}
