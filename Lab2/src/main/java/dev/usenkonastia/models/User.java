package dev.usenkonastia.models;

import dev.usenkonastia.validation.annotations.MaxValue;

public class User {
    public String username;

    @MaxValue(100)
    public Integer age;

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}