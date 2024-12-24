package dev.usenkonastia.validation.validators;

import java.lang.reflect.Field;

public class NotNullValidator implements FieldValidator {

    @Override
    public void validate(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(obj);

        if (value == null) {
            throw new IllegalArgumentException(field.getName() + " cannot be null.");
        }
    }
}
