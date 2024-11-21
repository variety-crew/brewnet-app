package com.varc.brewnetapp.exception;

public class InvalidApiRequestException extends RuntimeException {
    public InvalidApiRequestException(String message) {
        super(message);
    }
}
