package com.resired.api.guard.domain.exception;

public class ResidentNotFoundOnHomeException extends RuntimeException {
    public ResidentNotFoundOnHomeException(String lastFourDigits, Integer homeId) {
        super("No resident found with the last four digits of the document: " + lastFourDigits +
            ", on the home with id: " + homeId);
    }
}
