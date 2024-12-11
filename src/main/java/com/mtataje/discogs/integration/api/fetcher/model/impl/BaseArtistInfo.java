package com.mtataje.discogs.integration.api.fetcher.model.impl;

import com.mtataje.discogs.integration.api.fetcher.model.AlbumInfo;
import com.mtataje.discogs.integration.api.fetcher.model.ArtistInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BaseArtistInfo implements ArtistInfo {

    private Long id;
    private Long discogsId;
    private String name;
    private String realName;
    private Set<AlbumInfo> albums;

}
