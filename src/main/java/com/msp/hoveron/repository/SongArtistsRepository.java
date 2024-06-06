package com.msp.hoveron.repository;

import java.util.List;
import com.msp.hoveron.entity.Songartists;
import com.msp.hoveron.entity.Songs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongArtistsRepository extends JpaRepository<Songartists, Long> {
    List<Songartists> findSongartistsByArtist_ArtistId(Long artistId);

}