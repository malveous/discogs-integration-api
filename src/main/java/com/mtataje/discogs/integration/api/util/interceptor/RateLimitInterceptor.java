package com.mtataje.discogs.integration.api.util.interceptor;

import com.mtataje.discogs.integration.api.common.DiscogsExternalAPIProperties;
import com.mtataje.discogs.integration.api.exception.ConcurrentOperationFailureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class RateLimitInterceptor implements ClientHttpRequestInterceptor {

    private final DiscogsExternalAPIProperties properties;
    private int requestsRemaining;
    private final Object lock = new Object();

    @PostConstruct
    public void init() {
        this.requestsRemaining = this.properties.getRateLimit();
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        synchronized (lock) {
            if (requestsRemaining <= 0) {
                try {
                    log.info("Rate limit reached. Waiting for 1 second...");
                    Thread.sleep(properties.getRestTimeInMs());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new ConcurrentOperationFailureException(e);
                }
            }
        }

        ClientHttpResponse response = execution.execute(request, body);

        String rateLimitRemaining = response.getHeaders().getFirst("X-Discogs-Ratelimit-Remaining");
        if (rateLimitRemaining != null) {
            synchronized (lock) {
                requestsRemaining = Integer.parseInt(rateLimitRemaining);
                log.info("Updated rate limit remaining: {}", requestsRemaining);
            }
        }

        return response;
    }
}
