package com.mtataje.discogs.integration.api.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "discogs.api")
@Data
public class DiscogsExternalAPIProperties {

    private String baseApiUrl;
    private String apiKey;
    private int rateLimit;
    private int restTimeInMs;
    private int waitDurationInMs;
    private int maxAttempts;

}
