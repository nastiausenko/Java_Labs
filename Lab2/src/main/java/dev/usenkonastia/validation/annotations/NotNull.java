package dev.usenkonastia.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that a field must not be {@code null}.
 * <p>
 * This annotation is used for validation purposes to ensure that the
 * annotated field is not assigned a {@code null} value during runtime.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * public class Example {
 *     @NotNull
 *     private String name;
 * }
 * }
 * </pre>
 * </p>
 *
 * <p>
 * The {@code @NotNull} annotation is retained at runtime and
 * can be applied only to fields.
 * </p>
 *
 * @author Anastasiia Usenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {}
