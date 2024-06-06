package com.msp.hoveron.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistRequest {
    private String playlistName;
    private Integer userId;
    // Getters and setters
}