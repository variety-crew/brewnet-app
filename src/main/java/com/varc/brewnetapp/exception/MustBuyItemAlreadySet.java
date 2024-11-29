package com.varc.brewnetapp.exception;

public class MustBuyItemAlreadySet extends RuntimeException {
    public MustBuyItemAlreadySet(String message) {
        super(message);
    }
}
