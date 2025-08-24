package com.danimo.common.application.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Class clazz) {
        super(String.format("Entity of type {} not found", clazz.getCanonicalName()));
    }

    public UserNotFoundException(Class clazz, String message) {
        super(String.format("Entity of type {} not found: {}", clazz.getCanonicalName(), message));
    }
}
