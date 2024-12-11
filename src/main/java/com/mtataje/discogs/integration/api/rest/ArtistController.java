package com.mtataje.discogs.integration.api.rest;

import com.mtataje.discogs.integration.api.dto.ArtistResponseDTO;
import com.mtataje.discogs.integration.api.dto.ComparisonResultDTO;
import com.mtataje.discogs.integration.api.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistResponseDTO> getArtist(@PathVariable Long artistId,
                                                       @RequestParam(value = "source",
                                                               defaultValue = "api") String source) {
        return ResponseEntity.ok(artistService.findOrCreateArtist(artistId, source));
    }

    @PostMapping("/comparison/")
    public ResponseEntity<ComparisonResultDTO> compareArtists(@RequestBody Set<Long> artistIdsSet) {
        return ResponseEntity.ok(artistService.compareArtistsByArtistIdsSet(artistIdsSet));
    }

}
