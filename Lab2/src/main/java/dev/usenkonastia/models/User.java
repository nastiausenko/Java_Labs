package dev.usenkonastia.models;

import dev.usenkonastia.validation.annotations.MaxValue;
import dev.usenkonastia.validation.annotations.MinValue;
import dev.usenkonastia.validation.annotations.NotNull;
import dev.usenkonastia.validation.annotations.StringLength;

public class User {
    @NotNull
    @StringLength(min = 3, max = 20)
    public String username;

    @MinValue(18)
    @MaxValue(100)
    public Integer age;

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}