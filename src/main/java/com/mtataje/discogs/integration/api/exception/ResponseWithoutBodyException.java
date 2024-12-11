package com.mtataje.discogs.integration.api.exception;

public class ResponseWithoutBodyException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Response received without valid body data";

    public ResponseWithoutBodyException() {
        super(DEFAULT_MESSAGE);
    }

    public ResponseWithoutBodyException(String message) {
        super(message);
    }

    public ResponseWithoutBodyException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

}
