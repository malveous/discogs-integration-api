package com.mtataje.discogs.integration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ArtistResponseDTO {

    private Long id;
    private String name;
    private String realName;
    private Set<AlbumResponseDTO> albums = new HashSet<>();

}
