package com.university.scooterrental.exception;

public class ScooterAlreadyExistsException extends RuntimeException {
    public ScooterAlreadyExistsException(String message) {
        super(message);
    }
}
