package com.msp.hoveron.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "artists")
public class Artists implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artistid")
    private Long artistId;

    @Column(name = "artname", nullable = false)
    private String artName;

    @Column(name = "country", nullable = false)
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArtName() {
        return artName;
    }

    public void setArtName(String artName) {
        this.artName = artName;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

}
