package dev.usenkonastia.validation;

import dev.usenkonastia.validation.annotations.MaxValue;
import dev.usenkonastia.validation.annotations.MinValue;
import dev.usenkonastia.validation.validators.FieldValidator;
import dev.usenkonastia.validation.validators.MaxValueValidator;
import dev.usenkonastia.validation.validators.MinValueValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class Validator {
    private static final List<FieldValidator> validators = List.of(
            new MaxValueValidator(),
            new MinValueValidator()
    );

    public static void validate(Object obj) throws IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            for (FieldValidator validator : validators) {
                if (field.isAnnotationPresent(getAnnotationForValidator(validator))) {
                    validator.validate(obj, field);
                }
            }
        }
    }

    private static Class<? extends Annotation> getAnnotationForValidator(FieldValidator validator) {
        if (validator instanceof MaxValueValidator) {
            return MaxValue.class;
        }

        if (validator instanceof MinValueValidator) {
            return MinValue.class;
        }

        throw new IllegalArgumentException("Unknown validator: " + validator.getClass().getName());
    }
}