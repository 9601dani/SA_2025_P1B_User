package com.danimo.user.common.application.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("El username: " +message +" no existe");
    }

}
