package dev.usenkonastia.validation;

import dev.usenkonastia.validation.annotations.MaxValue;
import dev.usenkonastia.validation.annotations.MinValue;
import dev.usenkonastia.validation.annotations.NotNull;
import dev.usenkonastia.validation.annotations.StringLength;
import dev.usenkonastia.validation.validators.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class Validator {
    private static final List<FieldValidator> validators = List.of(
            new MaxValueValidator(),
            new MinValueValidator(),
            new NotNullValidator(),
            new StringLengthValidator()
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

        if (validator instanceof NotNullValidator) {
            return NotNull.class;
        }

        if (validator instanceof StringLengthValidator) {
            return StringLength.class;
        }

        throw new IllegalArgumentException("Unknown validator: " + validator.getClass().getName());
    }
}