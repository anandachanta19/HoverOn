package com.msp.hoveron.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contains")
public class Contains {

    @EmbeddedId
    private ContainsId id;

    @ManyToOne
    @MapsId("playlistId")
    @JoinColumn(name = "playlistid", nullable = false)
    private Playlists playlist;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "songid", nullable = false)
    private Songs song;

}
