package com.msp.hoveron.service;

import com.msp.hoveron.entity.Songs;

import java.util.List;

public interface SongService {
    Songs getSongById(Long songId);

    List<Songs> getAllSongs();
}
