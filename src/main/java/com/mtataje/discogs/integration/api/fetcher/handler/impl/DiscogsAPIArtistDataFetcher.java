package com.mtataje.discogs.integration.api.fetcher.handler.impl;

import com.mtataje.discogs.integration.api.client.DiscogsClient;
import com.mtataje.discogs.integration.api.fetcher.handler.ArtistDataFetcher;
import com.mtataje.discogs.integration.api.fetcher.model.AlbumInfo;
import com.mtataje.discogs.integration.api.fetcher.model.ArtistInfo;
import com.mtataje.discogs.integration.api.fetcher.model.impl.BaseArtistInfo;
import com.mtataje.discogs.integration.api.util.mapper.ReleaseAlbumDataMapper;
import com.mtataje.discogs.integration.api.vo.DiscogsArtistVO;
import com.mtataje.discogs.integration.api.vo.DiscogsReleaseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
@Qualifier("discogsAPIArtistDataFetcher")
public class DiscogsAPIArtistDataFetcher implements ArtistDataFetcher {

    private final DiscogsClient discogsClient;

    @Override
    public ArtistInfo fetchArtistDataByDiscogsId(Long id) {
        DiscogsArtistVO discogsArtistVO = discogsClient.getBaseArtistInfo(id);
        List<DiscogsReleaseVO> discogsReleaseVOList = discogsClient.getDiscogsReleaseWithDetailsFromReleaseUrl(
                discogsArtistVO.getReleasesUrl());

        Set<AlbumInfo> albums = ReleaseAlbumDataMapper.mapDiscogsReleaseVOListToAlbumInfoList(discogsReleaseVOList);

        return BaseArtistInfo.builder()
                .albums(albums)
                .discogsId(discogsArtistVO.getId())
                .name(discogsArtistVO.getName())
                .realName(discogsArtistVO.getRealName())
                .build();
    }
}
