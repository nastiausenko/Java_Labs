package dev.usenkonastia.models;

import dev.usenkonastia.validation.annotations.MaxValue;
import dev.usenkonastia.validation.annotations.MinValue;

public class User {
    public String username;

    @MinValue(18)
    @MaxValue(100)
    public Integer age;

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}