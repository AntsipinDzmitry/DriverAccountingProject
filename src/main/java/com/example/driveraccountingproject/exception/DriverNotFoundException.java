package com.example.driveraccountingproject.exception;

public class DriverNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public DriverNotFoundException(String message) {
        super(message);
    }
}
