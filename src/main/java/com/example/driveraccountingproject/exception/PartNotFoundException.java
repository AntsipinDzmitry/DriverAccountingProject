package com.example.driveraccountingproject.exception;

public class PartNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3;

    public PartNotFoundException(String message) {
        super(message);
    }
}
