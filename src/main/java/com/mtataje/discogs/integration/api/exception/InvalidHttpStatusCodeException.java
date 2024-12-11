package com.mtataje.discogs.integration.api.exception;

public class InvalidHttpStatusCodeException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid status code received";

    public InvalidHttpStatusCodeException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidHttpStatusCodeException(String message) {
        super(message);
    }

    public InvalidHttpStatusCodeException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

}
