package com.mtataje.discogs.integration.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsArtistVO {

    private Long id;
    private String name;
    @JsonProperty("resource_url")
    private String resourceUrl;
    private String uri;
    @JsonProperty("releases_url")
    private String releasesUrl;
    @JsonProperty("realname")
    private String realName;

}
