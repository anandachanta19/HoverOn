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
public class SongGenresId implements Serializable {
    @Column(name = "genreid")
    private Integer genreId;
    @Column(name = "songid")
    private Integer songId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SongGenresId)) return false;
        SongGenresId that = (SongGenresId) obj;
        return Objects.equals(getGenreId(), that.getGenreId()) &&
                Objects.equals(getSongId(), that.getSongId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGenreId(), getSongId());
    }

}
