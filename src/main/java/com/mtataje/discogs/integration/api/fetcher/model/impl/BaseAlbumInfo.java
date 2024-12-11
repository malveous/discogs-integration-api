package com.mtataje.discogs.integration.api.fetcher.model.impl;

import com.mtataje.discogs.integration.api.fetcher.model.AlbumInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BaseAlbumInfo implements AlbumInfo {

    private Long id;
    private Long discogsId;
    private Integer year;
    private String title;
    private String genres;
    private String styles;

}
