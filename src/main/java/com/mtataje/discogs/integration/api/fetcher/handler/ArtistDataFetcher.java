package com.mtataje.discogs.integration.api.fetcher.handler;

import com.mtataje.discogs.integration.api.fetcher.model.ArtistInfo;

public interface ArtistDataFetcher {

    ArtistInfo fetchArtistDataByDiscogsId(Long id);

}
