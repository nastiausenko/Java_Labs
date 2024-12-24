package dev.usenkonastia.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to validate the length of a {@code String} field.
 * <p>
 * This annotation allows specifying minimum and maximum length constraints
 * for a string. It is intended for use at the field level and is retained
 * at runtime to enable validation during application execution.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * @StringLength(min = 3, max = 10)
 * private String username;
 * }
 * </pre>
 *
 * <p>Default constraints:</p>
 * <ul>
 *   <li>Minimum length: {@code 0}</li>
 *   <li>Maximum length: {@code Integer.MAX_VALUE}</li>
 * </ul>
 *
 * @author Anastasiia Usenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StringLength {

    /**
     * Specifies the minimum length of the string.
     *
     * @return the minimum allowed length (default is {@code 0}).
     */
    int min() default 0;

    /**
     * Specifies the maximum length of the string.
     *
     * @return the maximum allowed length (default is {@code Integer.MAX_VALUE}).
     */
    int max() default Integer.MAX_VALUE;
}
