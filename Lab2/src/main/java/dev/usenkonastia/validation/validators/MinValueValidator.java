package dev.usenkonastia.validation.validators;

import dev.usenkonastia.validation.annotations.MinValue;

import java.lang.reflect.Field;

/**
 * Validator for fields annotated with {@link MinValue}.
 * <p>
 * This class ensures that numeric fields meet the minimum value constraint specified
 * by the {@link MinValue} annotation.
 * </p>
 *
 * @author Anastasiia Usenko
 */
public class MinValueValidator implements FieldValidator {

    /**
     * Validates the specified field of the given object against the {@link MinValue} annotation.
     *
     * <p>This method ensures that the field is numeric and its value does not exceed
     * the minimum allowed by the annotation. If the field does not meet these
     * criteria, an {@link IllegalArgumentException} is thrown.</p>
     *
     * @param obj   the object containing the field to validate
     * @param field the field to validate
     * @throws IllegalArgumentException if the field is not numeric or violates the constraint
     * @throws IllegalAccessException   if the field cannot be accessed
     */
    @Override
    public void validate(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(obj);

        if (!(value instanceof Number)) {
            throw new IllegalArgumentException("@MinValue can only be applied to numeric fields. Field: " + field.getName());
        }

        int intValue = ((Number) value).intValue();
        int minValue = field.getAnnotation(MinValue.class).value();

        if (intValue < minValue) {
            throw new IllegalArgumentException(field.getName() + " must be at least " + minValue);
        }
    }
}
