package dev.usenkonastia.models;

import dev.usenkonastia.validation.annotations.MaxValue;
import dev.usenkonastia.validation.annotations.MinValue;
import dev.usenkonastia.validation.annotations.NotNull;

public class User {
    @NotNull
    public String username;

    @MinValue(18)
    @MaxValue(100)
    public Integer age;

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}