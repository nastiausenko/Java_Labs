package dev.usenkonastia.processor.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to mark classes for SQL generation.
 * <p>
 * This annotation is retained only at the source level and applies to classes (types).
 * It can be used to identify classes that require SQL generation logic.
 * </p>
 *
 * @author Anastasiia Usenko
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface SqlGenerator {
}
