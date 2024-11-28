package com.varc.brewnetapp.exception;

public class ApprovalAlreadyCompleted extends RuntimeException {
    public ApprovalAlreadyCompleted(String message) {
        super(message);
    }
}
