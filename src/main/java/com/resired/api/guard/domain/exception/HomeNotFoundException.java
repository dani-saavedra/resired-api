package com.resired.api.guard.domain.exception;

public class HomeNotFoundException extends RuntimeException {
    public HomeNotFoundException(String block, String homeNumber) {
        super("Home not found with block: " + block + " and home number: " + homeNumber);
    }

    public HomeNotFoundException(String homeNumber) {
        super("Home not found with home number: " + homeNumber);
    }
}
