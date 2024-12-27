package dev.usenkonastia.processor.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to mark a class as representing a table in a database.
 * <p>
 * This annotation is used to specify the table name associated with the class.
 * It should be applied at the class level.
 * </p>
 *
 * @author Anastasiia Usenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    String name();
}
