package com.mtataje.discogs.integration.api.util;

import com.mtataje.discogs.integration.api.exception.ConcurrentOperationFailureException;
import org.springframework.web.client.HttpClientErrorException;

import java.util.function.Supplier;

public interface ConcurrentExecutable {

    default <T> T executeWithRetry(Supplier<T> supplier, long waitDuration, int maxAttempts) {
        int attempt = 0;

        while (attempt < maxAttempts) {
            try {
                return supplier.get();
            } catch (HttpClientErrorException.TooManyRequests e) {
                try {
                    Thread.sleep(waitDuration);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw new ConcurrentOperationFailureException(e);
                }
                attempt++;
            }
        }
        throw new ConcurrentOperationFailureException("Max retry attempts reached for the request.");
    }

}
