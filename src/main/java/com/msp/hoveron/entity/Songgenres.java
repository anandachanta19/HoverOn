package com.msp.hoveron.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "songgenres")
public class Songgenres implements java.io.Serializable {

    @EmbeddedId
    private SongGenresId id;

    @ManyToOne
    @MapsId("genreId") // Mapping for genreid
    @JoinColumn(name = "genreid", nullable = false)
    private Genres genre;

    @ManyToOne
    @MapsId("songId") // Mapping for songid
    @JoinColumn(name = "songid", nullable = false)
    private Songs song;


}
