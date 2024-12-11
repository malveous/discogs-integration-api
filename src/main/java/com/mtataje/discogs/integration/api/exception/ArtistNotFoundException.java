package com.mtataje.discogs.integration.api.exception;

public class ArtistNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "The artist requested was not found";

    public ArtistNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ArtistNotFoundException(String message) {
        super(message);
    }

    public ArtistNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

}
