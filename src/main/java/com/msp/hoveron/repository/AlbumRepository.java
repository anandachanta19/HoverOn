package com.msp.hoveron.repository;

import com.msp.hoveron.entity.Albums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Albums, Integer> {
    List<Albums> findByAlbNameContainingIgnoreCase(String query);
}
