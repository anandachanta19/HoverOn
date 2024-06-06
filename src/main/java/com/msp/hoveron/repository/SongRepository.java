package com.msp.hoveron.repository;

import com.msp.hoveron.entity.Songs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Songs, Long> {
    List<Songs> findBySongNameContainingIgnoreCase(String searchString);
    Songs findBySongId(Long songId);
}