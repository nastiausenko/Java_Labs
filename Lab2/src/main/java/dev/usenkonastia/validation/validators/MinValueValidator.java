package dev.usenkonastia.validation.validators;

import dev.usenkonastia.validation.annotations.MinValue;

import java.lang.reflect.Field;

public class MinValueValidator implements FieldValidator {

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
