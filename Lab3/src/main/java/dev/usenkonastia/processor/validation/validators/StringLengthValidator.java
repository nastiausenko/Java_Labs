package dev.usenkonastia.processor.validation.validators;

import dev.usenkonastia.processor.validation.annotations.StringLength;

import java.lang.reflect.Field;

/**
 * Validator for fields annotated with {@link StringLength}.
 * <p>
 * Ensures that the length of the String field value falls within
 * the specified minimum and maximum boundaries.
 * </p>
 *
 * @author Anastasiia Usenko
 */
public class StringLengthValidator implements FieldValidator {

    /**
     * Validates the given field of the specified object.
     * <p>
     * This method checks whether the field annotated with {@link StringLength}
     * contains a {@code String} value with a length within the defined range.
     * </p>
     *
     * @param obj   the object containing the field to validate
     * @param field the field to validate
     * @throws IllegalArgumentException if the field's value is not a {@code String}
     *                                  or if its length is outside the defined range
     * @throws IllegalAccessException   if the field cannot be accessed
     */
    @Override
    public void validate(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(obj);

        if (!(value instanceof String stringValue)) {
            throw new IllegalArgumentException("@StringLength can only be applied to String fields. Field: " + field.getName());
        }

        StringLength stringLength = field.getAnnotation(StringLength.class);

        if (stringValue.length() < stringLength.min() || stringValue.length() > stringLength.max()) {
            throw new IllegalArgumentException(field.getName() + " length must be between " + stringLength.min() + " and " + stringLength.max());
        }
    }
}
