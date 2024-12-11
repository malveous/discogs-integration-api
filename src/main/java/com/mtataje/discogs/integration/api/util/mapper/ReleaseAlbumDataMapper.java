package com.mtataje.discogs.integration.api.util.mapper;


import com.mtataje.discogs.integration.api.dto.AlbumResponseDTO;
import com.mtataje.discogs.integration.api.fetcher.model.AlbumInfo;
import com.mtataje.discogs.integration.api.fetcher.model.impl.BaseAlbumInfo;
import com.mtataje.discogs.integration.api.persistence.entity.Album;
import com.mtataje.discogs.integration.api.vo.DiscogsReleaseDetailsVO;
import com.mtataje.discogs.integration.api.vo.DiscogsReleaseVO;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ReleaseAlbumDataMapper {

    public Set<AlbumInfo> mapDiscogsReleaseVOListToAlbumInfoList(List<DiscogsReleaseVO> releases) {
        if (CollectionUtils.isEmpty(releases)) {
            return Set.of();
        }

        return releases.stream()
                .map(ReleaseAlbumDataMapper::mapSingleDiscogsReleaseVOToAlbumInfo)
                .collect(Collectors.toSet());
    }

    public AlbumInfo mapSingleDiscogsReleaseVOToAlbumInfo(DiscogsReleaseVO release) {
        Optional<DiscogsReleaseDetailsVO> detailsBox = Optional.ofNullable(release.getDetails());
        String genres = null;
        String styles = null;

        if (detailsBox.isPresent()) {
            DiscogsReleaseDetailsVO details = detailsBox.get();
            genres = String.join(",", details.getGenres());
            styles = String.join(",", details.getStyles());
        }

        return BaseAlbumInfo.builder()
                .discogsId(release.getId())
                .year(release.getYear())
                .title(release.getTitle())
                .genres(genres)
                .styles(styles)
                .build();
    }

    public Set<Album> mapBaseAlbumInfoSetToAlbumEntitySet(Set<AlbumInfo> albums) {
        if (CollectionUtils.isEmpty(albums)) {
            return Set.of();
        }

        return albums.stream()
                .map(ReleaseAlbumDataMapper::mapSingleAlbumInfoToAlbumEntity)
                .collect(Collectors.toSet());
    }

    public Album mapSingleAlbumInfoToAlbumEntity(AlbumInfo album) {
        return Album.builder()
                .title(album.getTitle())
                .discogsReleaseId(album.getDiscogsId())
                .year(album.getYear())
                .genres(album.getGenres())
                .styles(album.getStyles())
                .build();
    }

    public Set<AlbumResponseDTO> mapAlbumEntitySetToAlbumResponseDTOSet(Set<Album> albums) {
        if (CollectionUtils.isEmpty(albums)) {
            return Set.of();
        }

        return albums.stream()
                .map(ReleaseAlbumDataMapper::mapSingleAlbumEntityToAlbumResponseDTO)
                .collect(Collectors.toSet());
    }

    public AlbumResponseDTO mapSingleAlbumEntityToAlbumResponseDTO(Album album) {
        return AlbumResponseDTO.builder()
                .id(album.getId())
                .year(album.getYear())
                .title(album.getTitle())
                .genres(album.getGenres())
                .styles(album.getStyles())
                .build();
    }

    public Set<AlbumResponseDTO> mapAlbumInfoSetToAlbumResponseDTOSet(Set<AlbumInfo> albums) {
        if (CollectionUtils.isEmpty(albums)) {
            return Set.of();
        }

        return albums.stream()
                .map(ReleaseAlbumDataMapper::mapSingleAlbumInfoToAlbumResponseDTO)
                .collect(Collectors.toSet());
    }

    public AlbumResponseDTO mapSingleAlbumInfoToAlbumResponseDTO(AlbumInfo album) {
        return AlbumResponseDTO.builder()
                .id(album.getId())
                .year(album.getYear())
                .title(album.getTitle())
                .genres(album.getGenres())
                .styles(album.getStyles())
                .build();
    }

    public Set<AlbumInfo> mapAlbumEntitySetToAlbumInfoSet(Set<Album> albums) {
        if (CollectionUtils.isEmpty(albums)) {
            return Set.of();
        }

        return albums.stream()
                .map(ReleaseAlbumDataMapper::mapSingleAlbumEntityToAlbumInfo)
                .collect(Collectors.toSet());
    }

    public AlbumInfo mapSingleAlbumEntityToAlbumInfo(Album album) {
        return BaseAlbumInfo.builder()
                .id(album.getId())
                .title(album.getTitle())
                .discogsId(album.getDiscogsReleaseId())
                .genres(album.getGenres())
                .styles(album.getStyles())
                .year(album.getYear())
                .build();
    }

}
