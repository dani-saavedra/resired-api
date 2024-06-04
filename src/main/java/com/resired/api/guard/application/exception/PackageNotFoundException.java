package com.resired.api.guard.application.exception;

public class PackageNotFoundException extends RuntimeException {
    public PackageNotFoundException(Integer packageId) {
        super("Package with id " + packageId + " not found");
    }
}
