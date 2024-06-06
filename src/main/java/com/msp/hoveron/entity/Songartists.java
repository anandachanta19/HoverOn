package com.msp.hoveron.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "songartists")
public class Songartists implements Serializable {

    @EmbeddedId
    private SongArtistsId id;

    @ManyToOne
    @MapsId("artistId")
    @JoinColumn(name = "artistid", nullable = false)
    private Artists artist;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "songid", nullable = false)
    private Songs song;
}
