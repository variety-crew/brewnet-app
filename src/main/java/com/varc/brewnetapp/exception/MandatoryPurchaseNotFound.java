package com.varc.brewnetapp.exception;

public class MandatoryPurchaseNotFound extends RuntimeException {
    public MandatoryPurchaseNotFound(String message) {
        super(message);
    }
}
