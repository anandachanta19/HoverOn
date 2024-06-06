package com.msp.hoveron.repository;

import com.msp.hoveron.entity.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genres, Integer> {
    List<Genres> findByGenreNameContainingIgnoreCase(String query);
}
