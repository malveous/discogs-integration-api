package com.mtataje.discogs.integration.api.exception;

public class UnsupportedSourceForIntegrationAPIException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid source was requested for the integration API";

    public UnsupportedSourceForIntegrationAPIException() {
        super(DEFAULT_MESSAGE);
    }

    public UnsupportedSourceForIntegrationAPIException(String message) {
        super(message);
    }

    public UnsupportedSourceForIntegrationAPIException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

}
