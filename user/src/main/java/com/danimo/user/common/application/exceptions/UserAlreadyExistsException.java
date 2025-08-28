package com.danimo.user.common.application.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super("El usuario ya existe: " +message);
    }

    public UserAlreadyExistsException(String message, String message1) {
        super(message+": "+ message1);
    }
}
