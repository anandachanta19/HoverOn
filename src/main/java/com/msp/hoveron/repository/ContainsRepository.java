package com.msp.hoveron.repository;

import com.msp.hoveron.entity.Contains;
import com.msp.hoveron.entity.ContainsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainsRepository extends JpaRepository<Contains, ContainsId> {
    List<Contains> findByIdPlaylistId(Long playlistId);
    void deleteByIdPlaylistIdAndIdSongId(Long playlistId, Long songId);
    boolean existsByIdPlaylistIdAndIdSongId(Long playlistId, Long songId);
}