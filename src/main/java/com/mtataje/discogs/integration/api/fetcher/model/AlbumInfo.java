package com.mtataje.discogs.integration.api.fetcher.model;

public interface AlbumInfo {
    Long getId();
    Long getDiscogsId();
    Integer getYear();
    String getTitle();
    String getGenres();
    String getStyles();

}
