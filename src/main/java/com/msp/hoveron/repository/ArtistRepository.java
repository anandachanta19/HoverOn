package com.msp.hoveron.repository;

import com.msp.hoveron.entity.Artists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artists, Integer> {
    List<Artists> findByArtNameContainingIgnoreCase (String query);
}
