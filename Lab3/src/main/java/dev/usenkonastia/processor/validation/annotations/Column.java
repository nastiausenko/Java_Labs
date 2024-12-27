package dev.usenkonastia.processor.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code Column} annotation is used to specify the properties of a column
 * in a database or data structure.
 * It is applied to fields in a class to provide metadata about the column's
 * name and type.
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * @Column(name = "username", type = "VARCHAR")
 * private String username;
 * }
 * </pre>
 *
 * @author Anastasiia Usenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String name();
    String type();
}
