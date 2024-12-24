package dev.usenkonastia.validation.validators;

import dev.usenkonastia.validation.annotations.MaxValue;

import java.lang.reflect.Field;

public class MaxValueValidator implements FieldValidator {

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
