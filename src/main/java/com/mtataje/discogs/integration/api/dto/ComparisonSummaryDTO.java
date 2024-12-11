package com.mtataje.discogs.integration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ComparisonSummaryDTO {
    private Set<String> commonGenres;
    private Map<Long, Integer> albumCounts;
    private Map<Long, Integer> yearRanges;
}
