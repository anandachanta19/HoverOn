package com.msp.hoveron.repository;

import com.msp.hoveron.entity.Playlists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlists, Long> {
    List<Playlists> findByUserUserId(Long userId);
    Playlists deleteById(long id);
}
