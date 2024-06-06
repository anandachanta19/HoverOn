package com.msp.hoveron.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "albums")
public class Albums implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "albumid")
    private Long albumId;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "albname", nullable = false)
    private String albName;

    @ManyToOne
    @JoinColumn(name = "artistid", nullable = false)
    private Artists artist;

    public Artists getArtist() {
        return artist;
    }

    public void setArtist(Artists artist) {
        this.artist = artist;
    }

    public String getAlbName() {
        return albName;
    }

    public void setAlbName(String albName) {
        this.albName = albName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}

