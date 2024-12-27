package dev.usenkonastia.processor.validation;

import dev.usenkonastia.processor.validation.annotations.MaxValue;
import dev.usenkonastia.processor.validation.annotations.MinValue;
import dev.usenkonastia.processor.validation.annotations.NotNull;
import dev.usenkonastia.processor.validation.annotations.StringLength;
import dev.usenkonastia.processor.validation.validators.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Provides validation functionality for objects using custom annotations.
 * This class validates fields of an object based on annotations such as {@link MaxValue},
 * {@link MinValue}, {@link NotNull}, and {@link StringLength}.
 * <p>
 * Validators are dynamically matched to their corresponding annotations.
 * </p>
 *
 * @author Anastasiia Usenko
 */
public class Validator {

    /**
     * List of field validators used for validation.
     */
    private static final List<FieldValidator> validators = List.of(
            new MaxValueValidator(),
            new MinValueValidator(),
            new NotNullValidator(),
            new StringLengthValidator()
    );

    /**
     * Validates the fields of the given object based on custom annotations.
     * <p>
     * If a field contains a specific annotation, the corresponding validator
     * is invoked to validate the field.
     * </p>
     *
     * @param obj the object to validate
     * @throws IllegalArgumentException if validation fails
     * @throws IllegalAccessException   if field access is restricted
     */
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

    /**
     * Matches a validator with its corresponding annotation class.
     *
     * @param validator the validator instance
     * @return the annotation class corresponding to the validator
     * @throws IllegalArgumentException if the validator is not recognized
     */
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