package com.university.scooterrental.exception;

public class ScooterNotFoundException extends RuntimeException {
    public ScooterNotFoundException(String message) {
        super(message);
    }
}
