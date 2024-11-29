package com.varc.brewnetapp.exception;

public class InvalidOrderApproval extends RuntimeException {
    public InvalidOrderApproval(String message) {
        super(message);
    }
}
