package dev.usenkonastia.validation.validators;

import dev.usenkonastia.validation.annotations.StringLength;

import java.lang.reflect.Field;

public class StringLengthValidator implements FieldValidator {

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
