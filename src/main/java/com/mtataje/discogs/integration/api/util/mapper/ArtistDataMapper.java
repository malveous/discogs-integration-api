package com.mtataje.discogs.integration.api.util.mapper;

import com.mtataje.discogs.integration.api.dto.ArtistComparisonDTO;
import com.mtataje.discogs.integration.api.dto.ArtistResponseDTO;
import com.mtataje.discogs.integration.api.fetcher.model.ArtistInfo;
import com.mtataje.discogs.integration.api.fetcher.model.impl.BaseArtistInfo;
import com.mtataje.discogs.integration.api.persistence.entity.Album;
import com.mtataje.discogs.integration.api.persistence.entity.Artist;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ArtistDataMapper {

    public ArtistResponseDTO mapArtistEntityToArtistResponseDTO(Artist artistEntity) {
        return ArtistResponseDTO.builder()
                .id(artistEntity.getId())
                .name(artistEntity.getName())
                .realName(artistEntity.getRealName())
                .albums(ReleaseAlbumDataMapper.mapAlbumEntitySetToAlbumResponseDTOSet(artistEntity.getAlbums()))
                .build();
    }

    public ArtistResponseDTO mapBaseArtistInfoToArtistResponseDTO(ArtistInfo artistInfo) {
        return ArtistResponseDTO.builder()
                .id(artistInfo.getId())
                .name(artistInfo.getName())
                .realName(artistInfo.getRealName())
                .albums(ReleaseAlbumDataMapper.mapAlbumInfoSetToAlbumResponseDTOSet(artistInfo.getAlbums()))
                .build();
    }

    public ArtistInfo mapArtistEntityToBaseArtistInfo(Artist artist) {
        return BaseArtistInfo.builder()
                .id(artist.getId())
                .discogsId(artist.getDiscogsId())
                .name(artist.getName())
                .realName(artist.getRealName())
                .albums(ReleaseAlbumDataMapper.mapAlbumEntitySetToAlbumInfoSet(artist.getAlbums()))
                .build();
    }

    public Artist mapBaseArtistInfoToArtistEntity(ArtistInfo artistInfo) {
        return Artist.builder()
                .realName(artistInfo.getRealName())
                .name(artistInfo.getName())
                .discogsId(artistInfo.getDiscogsId())
                .id(artistInfo.getId())
                .albums(ReleaseAlbumDataMapper.mapBaseAlbumInfoSetToAlbumEntitySet(artistInfo.getAlbums()))
                .build();
    }


    public static ArtistComparisonDTO mapArtistEntityToArtistComparisonDTO(Artist artist) {
        Set<String> genres = artist.getAlbums().stream()
                .map(Album::getGenres)
                .flatMap(g -> Arrays.stream(g.split(",")))
                .collect(Collectors.toSet());

        OptionalInt earliestYear = artist.getAlbums().stream().mapToInt(Album::getYear).min();
        OptionalInt latestYear = artist.getAlbums().stream().mapToInt(Album::getYear).max();

        return new ArtistComparisonDTO(
                artist.getDiscogsId(),
                artist.getName(),
                String.join(", ", genres),
                artist.getAlbums().size(),
                earliestYear.isPresent() ? earliestYear.getAsInt() : null,
                latestYear.isPresent() ? latestYear.getAsInt() : null
        );
    }
}
