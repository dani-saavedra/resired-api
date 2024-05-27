package com.resired.api.guard.domain.exception;

public class HomeNotFoundException extends RuntimeException {
    public HomeNotFoundException() {
        super("Home not found with block and home number provided");
    }
}
