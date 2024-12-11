package com.mtataje.discogs.integration.api.persistence.repository;

import com.mtataje.discogs.integration.api.persistence.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByDiscogsId(Long discogsId);

    @Query("SELECT a FROM Artist a JOIN FETCH a.albums WHERE a.discogsId = :discogsId")
    Optional<Artist> findByDiscogsIdWithAlbums(@Param("discogsId") Long discogsId);
}
