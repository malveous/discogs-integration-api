package com.mtataje.discogs.integration.api.fetcher.handler.impl;

import com.mtataje.discogs.integration.api.exception.ArtistNotFoundException;
import com.mtataje.discogs.integration.api.fetcher.handler.ArtistDataFetcher;
import com.mtataje.discogs.integration.api.fetcher.model.ArtistInfo;
import com.mtataje.discogs.integration.api.persistence.entity.Artist;
import com.mtataje.discogs.integration.api.persistence.repository.ArtistRepository;
import com.mtataje.discogs.integration.api.util.mapper.ArtistDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@Qualifier("rdbsArtistDataFetcher")
@RequiredArgsConstructor
public class RDBSArtistDataFetcher implements ArtistDataFetcher {

    private ArtistRepository artistRepository;

    @Override
    public ArtistInfo fetchArtistDataByDiscogsId(Long id) {
        Optional<Artist> artistBox = this.artistRepository.findByDiscogsId(id);
        if (artistBox.isPresent()) {
            return ArtistDataMapper.mapArtistEntityToBaseArtistInfo(artistBox.get());
        } else {
            throw new ArtistNotFoundException();
        }
    }
}
