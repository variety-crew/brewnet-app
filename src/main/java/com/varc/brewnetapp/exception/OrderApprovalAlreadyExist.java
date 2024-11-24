package com.varc.brewnetapp.exception;

public class OrderApprovalAlreadyExist extends RuntimeException {
    public OrderApprovalAlreadyExist(String message) {
        super(message);
    }
}
