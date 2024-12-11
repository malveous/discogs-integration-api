package com.mtataje.discogs.integration.api.service;

import com.mtataje.discogs.integration.api.dto.ArtistResponseDTO;
import com.mtataje.discogs.integration.api.dto.ComparisonResultDTO;

import java.util.List;
import java.util.Set;

public interface ArtistService {

    ArtistResponseDTO findOrCreateArtist(Long artistId, String source);

    ComparisonResultDTO compareArtistsByArtistIdsSet(Set<Long> artistIdsSet);
}
