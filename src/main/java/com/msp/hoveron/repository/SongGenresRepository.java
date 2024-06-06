package com.msp.hoveron.repository;

import com.msp.hoveron.entity.Songgenres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongGenresRepository extends JpaRepository<Songgenres, Integer> {
    List<Songgenres> findSonggenresByGenre_GenreId(Long genreId);
}
