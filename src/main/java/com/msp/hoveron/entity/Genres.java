package com.msp.hoveron.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "genres")
public class Genres implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genreid")
    private Long genreId;

    @Column(name = "genrename", nullable = false)
    private String genreName;

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
