package com.mtataje.discogs.integration.api.exception;

public class NoResponseDataException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "No response data received";

    public NoResponseDataException() {
        super(DEFAULT_MESSAGE);
    }

    public NoResponseDataException(String message) {
        super(message);
    }

    public NoResponseDataException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

}
