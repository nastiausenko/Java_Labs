package dev.usenkonastia.models;

import dev.usenkonastia.validation.annotations.MaxValue;
import dev.usenkonastia.validation.annotations.MinValue;
import dev.usenkonastia.validation.annotations.NotNull;
import dev.usenkonastia.validation.annotations.StringLength;

public class User {
    @NotNull
    @StringLength(min = 3, max = 20)
    private String username;

    @MinValue(18)
    @MaxValue(100)
    private Integer age;

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