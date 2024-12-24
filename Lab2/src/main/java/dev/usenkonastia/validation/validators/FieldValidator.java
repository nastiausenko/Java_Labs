package dev.usenkonastia.validation.validators;

import java.lang.reflect.Field;

public interface FieldValidator {
    void validate(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException;
}
