package com.varc.brewnetapp.exception;

public class OrderDraftAlreadyApproved extends RuntimeException {
    public OrderDraftAlreadyApproved(String message) {
        super(message);
    }
}
