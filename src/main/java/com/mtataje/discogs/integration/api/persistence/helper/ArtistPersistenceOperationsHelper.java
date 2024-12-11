package com.mtataje.discogs.integration.api.persistence.helper;

import com.mtataje.discogs.integration.api.fetcher.model.ArtistInfo;
import com.mtataje.discogs.integration.api.persistence.entity.Album;
import com.mtataje.discogs.integration.api.persistence.entity.Artist;
import com.mtataje.discogs.integration.api.persistence.repository.ArtistRepository;
import com.mtataje.discogs.integration.api.util.mapper.ArtistDataMapper;
import com.mtataje.discogs.integration.api.util.mapper.ReleaseAlbumDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArtistPersistenceOperationsHelper {

    private final ArtistRepository artistRepository;

    public Artist saveOrUpdateArtistInfo(ArtistInfo artistInfo) {
        Optional<Artist> existingArtist = artistRepository.findByDiscogsId(artistInfo.getDiscogsId());
        Artist artistEntity;

        if (existingArtist.isPresent()) {
            artistEntity = existingArtist.get();
            boolean sameName = artistInfo.getName().equals(artistEntity.getName());
            boolean sameRealName = artistInfo.getRealName().equals(artistEntity.getRealName());

            if (!sameName) {
                artistEntity.setName(artistInfo.getName());
            }

            if (!sameRealName) {
                artistEntity.setRealName(artistInfo.getRealName());
            }

            Set<Album> existingAlbums = artistEntity.getAlbums();
            Set<Album> newAlbums = ReleaseAlbumDataMapper.mapBaseAlbumInfoSetToAlbumEntitySet(artistInfo.getAlbums());
            Map<Long, Album> existingAlbumMap = existingAlbums.stream()
                    .collect(Collectors.toMap(Album::getDiscogsReleaseId, album -> album));

            for (Album newAlbum : newAlbums) {
                if (!existingAlbumMap.containsKey(newAlbum.getDiscogsReleaseId())) {
                    newAlbum.setArtist(artistEntity);
                    existingAlbums.add(newAlbum);
                }
            }
            artistEntity = artistRepository.save(artistEntity);
        } else {
            artistEntity = artistRepository.save(ArtistDataMapper.mapBaseArtistInfoToArtistEntity(artistInfo));
        }
        return artistEntity;
    }

}
