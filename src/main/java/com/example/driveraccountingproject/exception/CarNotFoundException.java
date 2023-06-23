package com.example.driveraccountingproject.exception;

public class CarNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2;

    public CarNotFoundException(String message) {
        super(message);
    }
}
