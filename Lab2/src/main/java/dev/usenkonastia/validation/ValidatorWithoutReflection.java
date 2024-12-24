package dev.usenkonastia.validation;

public interface ValidatorWithoutReflection<T> {
    void validate(T obj);
}
