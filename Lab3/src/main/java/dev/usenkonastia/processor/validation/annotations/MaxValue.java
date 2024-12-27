package dev.usenkonastia.processor.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify the maximum value constraint for a field.
 * <p>
 * This annotation can be applied to numeric fields only, to indicate that the value of the annotated field
 * should not exceed the specified {@code value}.
 * </p>
 * <p>
 * The annotation can be applied to fields, and it is retained at runtime.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * public class Example {
 *     @MaxValue(100)
 *     private int age;
 * }
 * }
 * </pre>
 *
 * @author Anastasiia Usenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MaxValue {
    int value();
}