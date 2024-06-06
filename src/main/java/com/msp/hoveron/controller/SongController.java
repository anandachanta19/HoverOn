package com.msp.hoveron.controller;

import com.msp.hoveron.entity.Songs;
import com.msp.hoveron.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/{songId}")
    public ResponseEntity<?> getSongById(@PathVariable String songId) {
        try {
            Long parsedSongId = Long.parseLong(songId);
            Songs song = songService.getSongById(parsedSongId);
            if (song == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found");
            }
            return ResponseEntity.ok(song);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid song ID format");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSongs() {
        List<Songs> allSongs = songService.getAllSongs();
        Collections.shuffle(allSongs); // Shuffle the list to randomize the order
        return ResponseEntity.ok(allSongs);
    }
}
