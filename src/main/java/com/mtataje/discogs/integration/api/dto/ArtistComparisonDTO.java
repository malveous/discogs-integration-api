package com.mtataje.discogs.integration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ArtistComparisonDTO {
    private Long discogsId;
    private String name;
    private String genres;
    private int numberOfAlbums;
    private Integer earliestAlbumYear;
    private Integer latestAlbumYear;
}
