package com.varc.brewnetapp.exception;

public class MemberNotInFranchiseException extends RuntimeException {
    public MemberNotInFranchiseException(String message) {
        super(message);
    }
}
