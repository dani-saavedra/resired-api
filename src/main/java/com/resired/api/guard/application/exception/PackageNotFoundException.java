package com.resired.api.guard.application.exception;

public class PackageNotFoundException extends RuntimeException {
    public PackageNotFoundException(Integer packageId, Integer neighborhoodId) {
        super("Package with id " + packageId + " not found on neighborhood with id: " + neighborhoodId);
    }
}
