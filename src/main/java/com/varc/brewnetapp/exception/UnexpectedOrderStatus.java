package com.varc.brewnetapp.exception;

public class UnexpectedOrderStatus extends RuntimeException {
    public UnexpectedOrderStatus(String message) {
        super(message);
    }
}
