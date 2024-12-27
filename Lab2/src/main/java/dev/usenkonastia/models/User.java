package dev.usenkonastia.models;

import dev.usenkonastia.processor.validation.annotations.Column;
import dev.usenkonastia.processor.validation.annotations.SqlGenerator;
import dev.usenkonastia.processor.validation.annotations.Table;
import dev.usenkonastia.processor.validation.annotations.MaxValue;
import dev.usenkonastia.processor.validation.annotations.MinValue;
import dev.usenkonastia.processor.validation.annotations.NotNull;
import dev.usenkonastia.processor.validation.annotations.StringLength;
import lombok.Builder;
import lombok.Getter;

/**
 * Represents a user with username and age, validated using custom annotations.
 *
 * @author Anastasiia Usenko
 */
@SqlGenerator
@Getter
@Builder
@Table(name = "users")
public class User {

    @Column(name = "username", type = "TEXT")
    @NotNull
    @StringLength(min = 3, max = 20)
    private String username;

    @Column(name = "age", type = "INT")
    @MinValue(18)
    @MaxValue(100)
    private Integer age;

    @Column(name = "password", type = "TEXT")
    @NotNull
    private String password;
}