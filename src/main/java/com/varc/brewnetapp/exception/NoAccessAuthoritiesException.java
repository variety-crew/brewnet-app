package com.varc.brewnetapp.exception;

public class NoAccessAuthoritiesException extends RuntimeException {
    public NoAccessAuthoritiesException(String message) {
        super(message);
    }
}
