package com.varc.brewnetapp.exception;

public class OrderItemNotFound extends RuntimeException {
    public OrderItemNotFound(String message) {
        super(message);
    }
}
