package com.danimo.user.common.application.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super("El usuario ya existe: " +message);
    }
}
