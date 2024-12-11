package com.mtataje.discogs.integration.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscogsReleaseDetailsVO {

    @JsonProperty("id")
    private Long releaseId;
    private List<String> genres;
    private List<String> styles;

}
