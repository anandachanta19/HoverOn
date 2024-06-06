package com.msp.hoveron.controller;

import com.msp.hoveron.entity.Songs;
import com.msp.hoveron.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class MediaPlayerController {

    private final SongService songService;

    @Autowired
    public MediaPlayerController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/mediaplayer/{songId}")
    public String showMediaPlayerPage(@PathVariable Long songId, Model model) {
        if (songId == null) {
            return "error"; // Redirect to an error page
        }
        Songs song = songService.getSongById(songId);
        if (song != null) {
            model.addAttribute("song", song);
            return "mediaplayer"; // Return the mediaplayer.html template
        } else {
            return "error"; // Handle the case when song is not found, return an error page
        }
    }

}
