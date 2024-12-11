package com.mtataje.discogs.integration.api.util.calc;

import com.mtataje.discogs.integration.api.dto.ArtistComparisonDTO;
import com.mtataje.discogs.integration.api.dto.ComparisonSummaryDTO;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DataComparisonUtility {

    public ComparisonSummaryDTO calculateComparisonSummary(Set<ArtistComparisonDTO> artists) {
        Set<String> commonGenres = artists.stream()
                .map(ArtistComparisonDTO::getGenres)
                .map(g -> new HashSet<>(Arrays.asList(g.split(", "))))
                .reduce((a, b) -> {
                    a.retainAll(b);
                    return a;
                })
                .orElse(new HashSet<>());

        Map<Long, Integer> albumCounts = artists.stream()
                .collect(Collectors.toMap(ArtistComparisonDTO::getDiscogsId, ArtistComparisonDTO::getNumberOfAlbums));

        Map<Long, Integer> yearRanges = artists.stream()
                .collect(Collectors.toMap(ArtistComparisonDTO::getDiscogsId,
                        artist -> artist.getLatestAlbumYear() - artist.getEarliestAlbumYear()));

        return new ComparisonSummaryDTO(commonGenres, albumCounts, yearRanges);
    }

}
