package com.mtataje.discogs.integration.api.service.impl;

import com.mtataje.discogs.integration.api.common.ValidSource;
import com.mtataje.discogs.integration.api.dto.ArtistComparisonDTO;
import com.mtataje.discogs.integration.api.dto.ArtistResponseDTO;
import com.mtataje.discogs.integration.api.dto.ComparisonResultDTO;
import com.mtataje.discogs.integration.api.dto.ComparisonSummaryDTO;
import com.mtataje.discogs.integration.api.exception.UnsupportedSourceForIntegrationAPIException;
import com.mtataje.discogs.integration.api.fetcher.handler.impl.DiscogsAPIArtistDataFetcher;
import com.mtataje.discogs.integration.api.fetcher.handler.impl.RDBSArtistDataFetcher;
import com.mtataje.discogs.integration.api.fetcher.model.ArtistInfo;
import com.mtataje.discogs.integration.api.persistence.entity.Artist;
import com.mtataje.discogs.integration.api.persistence.helper.ArtistPersistenceOperationsHelper;
import com.mtataje.discogs.integration.api.persistence.repository.ArtistRepository;
import com.mtataje.discogs.integration.api.service.ArtistService;
import com.mtataje.discogs.integration.api.util.calc.DataComparisonUtility;
import com.mtataje.discogs.integration.api.util.mapper.ArtistDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final RDBSArtistDataFetcher rdbsArtistDataFetcher;
    private final DiscogsAPIArtistDataFetcher discogsApiArtistDataFetcher;
    private final ArtistPersistenceOperationsHelper persistenceOperationsHelper;
    private final ArtistRepository artistRepository;

    @Override
    public ArtistResponseDTO findOrCreateArtist(final Long artistId, final String source) {
        ArtistResponseDTO artistDto;
        ArtistInfo artistInfo;
        Artist artistEntity;

        if (source.equals(ValidSource.API.getValue())) {
            artistInfo = discogsApiArtistDataFetcher.fetchArtistDataByDiscogsId(artistId);
            artistEntity = persistenceOperationsHelper.saveOrUpdateArtistInfo(artistInfo);
            artistDto = ArtistDataMapper.mapArtistEntityToArtistResponseDTO(artistEntity);
        } else if (source.equals(ValidSource.DATABASE.getValue())) {
            artistInfo = rdbsArtistDataFetcher.fetchArtistDataByDiscogsId(artistId);
            artistDto = ArtistDataMapper.mapBaseArtistInfoToArtistResponseDTO(artistInfo);
        } else {
            throw new UnsupportedSourceForIntegrationAPIException();
        }

        return artistDto;
    }

    @Override
    public ComparisonResultDTO compareArtistsByArtistIdsSet(Set<Long> artistIdsSet) {
        Set<ArtistComparisonDTO> artists = new HashSet<>();

        for (Long discogsId : artistIdsSet) {
            Optional<Artist> artistOptional = artistRepository.findByDiscogsId(discogsId);

            Artist artist = artistOptional.orElseGet(() -> {
                ArtistInfo artistInfo = discogsApiArtistDataFetcher.fetchArtistDataByDiscogsId(discogsId);
                return persistenceOperationsHelper.saveOrUpdateArtistInfo(artistInfo);
            });

            artists.add(ArtistDataMapper.mapArtistEntityToArtistComparisonDTO(artist));
        }

        ComparisonSummaryDTO summary = DataComparisonUtility.calculateComparisonSummary(artists);

        ComparisonResultDTO comparisonResult = new ComparisonResultDTO();
        comparisonResult.setArtists(artists);
        comparisonResult.setSummary(summary);
        return comparisonResult;
    }

}
