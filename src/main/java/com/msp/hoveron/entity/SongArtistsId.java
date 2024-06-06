package com.msp.hoveron.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class SongArtistsId implements Serializable {

    @Column(name = "songid")
    private Integer songId;

    @Column(name = "artistid")
    private Integer artistId;
    public SongArtistsId() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SongArtistsId)) return false;
        SongArtistsId that = (SongArtistsId) obj;
        return Objects.equals(getArtistId(), that.getArtistId()) &&
                Objects.equals(getSongId(), that.getSongId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArtistId(), getSongId());
    }

}
