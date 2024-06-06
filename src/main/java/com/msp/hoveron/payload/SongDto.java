package com.msp.hoveron.payload;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SongDto implements Serializable {
    private String songName;
    private Long songId;

    public SongDto(String songName, Long songId) {
        this.songName = songName;
        this.songId = songId;
    }
}
