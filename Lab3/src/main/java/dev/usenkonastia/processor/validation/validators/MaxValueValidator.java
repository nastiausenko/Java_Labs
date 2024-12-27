package dev.usenkonastia.processor.validation.validators;

import dev.usenkonastia.processor.validation.annotations.MaxValue;

import java.lang.reflect.Field;

/**
 * Validator to ensure that numeric fields annotated with {@link MaxValue}
 * do not exceed the specified maximum value.
 *
 * <p>Checks if the field value is numeric and compares it
 * against the value provided in the {@link MaxValue} annotation.</p>
 *
 * @author Anastasiia Usenko
 */
public class MaxValueValidator implements FieldValidator {

    /**
     * Validates the specified field of the given object against the {@link MaxValue} annotation.
     *
     * <p>Ensures that the field is numeric and its value does not exceed
     * the maximum allowed by the annotation. If the field does not meet these
     * criteria, an {@link IllegalArgumentException} is thrown.</p>
     *
     * @param obj   the object containing the field to validate
     * @param field the field to validate
     * @throws IllegalArgumentException if the field is not numeric or exceeds the maximum value
     * @throws IllegalAccessException   if the field cannot be accessed
     */
    @Override
    public void validate(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(obj);

        if (!(value instanceof Number)) {
            throw new IllegalArgumentException("@MaxValue can only be applied to numeric fields. Field: " + field.getName());
        }

        int intValue = ((Number) value).intValue();
        int maxValue = field.getAnnotation(MaxValue.class).value();

        if (intValue > maxValue) {
            throw new IllegalArgumentException(field.getName() + " must be at most " + maxValue);
        }
    }
}
