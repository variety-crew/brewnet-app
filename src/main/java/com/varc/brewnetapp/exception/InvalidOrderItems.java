package com.varc.brewnetapp.exception;

public class InvalidOrderItems extends RuntimeException {
    public InvalidOrderItems(String message) {
        super(message);
    }
}
