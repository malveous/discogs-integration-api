package com.mtataje.discogs.integration.api.fetcher.model;

import java.util.Set;

public interface ArtistInfo {
    Long getId();
    Long getDiscogsId();
    String getName();
    String getRealName();
    Set<AlbumInfo> getAlbums();

}
