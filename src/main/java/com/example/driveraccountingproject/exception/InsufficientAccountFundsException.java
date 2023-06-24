package com.example.driveraccountingproject.exception;

public class InsufficientAccountFundsException extends RuntimeException{
    private static final long serialVersionUID = 4;

    public InsufficientAccountFundsException(String message) {
        super(message);
    }
}
