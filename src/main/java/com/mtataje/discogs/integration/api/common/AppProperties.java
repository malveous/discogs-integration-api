package com.mtataje.discogs.integration.api.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Data
@Validated
@ConfigurationProperties(prefix = "app.integration.config")
public class AppProperties {

    private String name;
    private String version;
    private int poolSize;

}
