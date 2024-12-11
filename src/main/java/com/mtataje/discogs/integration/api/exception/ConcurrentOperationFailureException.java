package com.mtataje.discogs.integration.api.exception;

public class ConcurrentOperationFailureException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "An error occurred while executing a concurrent operation";

    public ConcurrentOperationFailureException() {
        super(DEFAULT_MESSAGE);
    }

    public ConcurrentOperationFailureException(String message) {
        super(message);
    }

    public ConcurrentOperationFailureException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

}
