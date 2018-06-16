package com.companies.error;

public class DuplicateContentException extends RuntimeException {

    public DuplicateContentException(final String message) {
        super(message);
    }
}
