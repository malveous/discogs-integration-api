package com.mtataje.discogs.integration.api.persistence.repository;

import com.mtataje.discogs.integration.api.persistence.entity.Album;
import com.mtataje.discogs.integration.api.persistence.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByArtistOrderByYear(Artist artist);

}
