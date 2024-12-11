package com.mtataje.discogs.integration.api.config;

import com.mtataje.discogs.integration.api.common.AppProperties;
import com.mtataje.discogs.integration.api.common.DiscogsExternalAPIProperties;
import com.mtataje.discogs.integration.api.util.interceptor.RateLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Component
@RequiredArgsConstructor
public class BaseIntegrationConfig {

    private final AppProperties appProperties;
    private final DiscogsExternalAPIProperties discogsExternalAPIProperties;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .defaultHeader(HttpHeaders.USER_AGENT, String.format("%s/%s", appProperties.getName(),
                        appProperties.getVersion()))
                .interceptors(new RateLimitInterceptor(discogsExternalAPIProperties))
                .build();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(appProperties.getPoolSize());
    }

}
