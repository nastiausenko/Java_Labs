package dev.usenkonastia.models;

import dev.usenkonastia.processor.Column;
import dev.usenkonastia.processor.SqlGenerator;
import dev.usenkonastia.processor.Table;
import dev.usenkonastia.processor.validation.annotations.MaxValue;
import dev.usenkonastia.processor.validation.annotations.MinValue;
import dev.usenkonastia.processor.validation.annotations.NotNull;
import dev.usenkonastia.processor.validation.annotations.StringLength;

/**
 * Represents a user with username and age, validated using custom annotations.
 *
 * @author Anastasiia Usenko
 */
@SqlGenerator
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
    private String password;

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public Integer getAge() {
        return age;
    }
}