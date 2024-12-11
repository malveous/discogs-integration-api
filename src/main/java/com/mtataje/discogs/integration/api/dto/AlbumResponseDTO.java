package com.mtataje.discogs.integration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AlbumResponseDTO {

    private Long id;
    private String title;
    private int year;
    private String genres;
    private String styles;

}
