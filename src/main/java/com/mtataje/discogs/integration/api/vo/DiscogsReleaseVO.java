package com.mtataje.discogs.integration.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsReleaseVO {
    private Long id;
    private String title;
    private Integer year;
    @JsonProperty("resource_url")
    private String resourceUrl;
    @JsonIgnore
    private DiscogsReleaseDetailsVO details;
}
