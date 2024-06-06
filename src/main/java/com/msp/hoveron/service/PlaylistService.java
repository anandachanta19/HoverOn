package com.msp.hoveron.service;

import com.msp.hoveron.entity.Playlists;
import com.msp.hoveron.payload.SongDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public interface PlaylistService {
    Playlists createPlaylist(String name,Integer userId);

    Playlists deletePlaylistByPlaylistId(Long playlistId);
    List<Playlists> getPlaylistsByUserId(Long userId);
    List<SongDto> getSongsForPlaylist(Long playlistId);
    void removeSongFromPlaylist(Long songId, Long playlistId);
    void addSongToPlaylist(Long songId, Long playlistId);

    boolean isSongInPlaylist(Long songId, Long playlistId);
}
