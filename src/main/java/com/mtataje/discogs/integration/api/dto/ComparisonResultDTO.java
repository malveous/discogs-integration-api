package com.mtataje.discogs.integration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ComparisonResultDTO {

    private Set<ArtistComparisonDTO> artists;
    private ComparisonSummaryDTO summary;

}
