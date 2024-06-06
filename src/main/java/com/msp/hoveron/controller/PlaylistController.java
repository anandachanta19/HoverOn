package com.msp.hoveron.controller;

import com.msp.hoveron.entity.Playlists;
import com.msp.hoveron.payload.PlaylistRequest;
import com.msp.hoveron.payload.SongDto;
import com.msp.hoveron.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/hoveron")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping("/createPlaylist")
    public ResponseEntity<Playlists> createPlaylist(@RequestBody PlaylistRequest request) {
        Playlists savedPlaylist = playlistService.createPlaylist(request.getPlaylistName(), request.getUserId());
        return new ResponseEntity<>(savedPlaylist, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylistByPlaylistId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // PlaylistController.java
    @GetMapping("/user/playlists/{userId}")
    public ResponseEntity<List<Playlists>> getUserPlaylists(@PathVariable Long userId) {
        List<Playlists> playlists = playlistService.getPlaylistsByUserId(userId);
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

    @GetMapping("/playlist/songs/{playlistId}")
    public ResponseEntity<List<SongDto>> getPlaylistSongs(@PathVariable Long playlistId) {
        List<SongDto> songs = playlistService.getSongsForPlaylist(playlistId);
        return ResponseEntity.ok(songs);
    }

    @DeleteMapping("/playlist/removeSong")
    public ResponseEntity<Void> removeSongFromPlaylist(@RequestBody Map<String, Long> requestBody) {
        Long songId = requestBody.get("songId");
        Long playlistId = requestBody.get("playlistId");
        playlistService.removeSongFromPlaylist(songId, playlistId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/playlist/addSong")
    public ResponseEntity<?> addSongToPlaylist(@RequestBody Map<String, Long> requestBody) {
        Long songId = requestBody.get("songId");
        Long playlistId = requestBody.get("playlistId");
        if (playlistService.isSongInPlaylist(songId, playlistId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Song is already in the playlist");
        } else {
            try {
                playlistService.addSongToPlaylist(songId, playlistId);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding song to playlist: " + e.getMessage());
            }
        }
    }


}
