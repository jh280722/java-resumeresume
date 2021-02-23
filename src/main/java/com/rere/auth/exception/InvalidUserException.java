package com.rere.auth.exception;

public class InvalidUserException extends IllegalArgumentException {
    public InvalidUserException(String message) {
        super(message);
    }
}
