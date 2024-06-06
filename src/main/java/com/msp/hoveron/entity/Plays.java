package com.msp.hoveron.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "plays")
public class Plays {

    @EmbeddedId
    private PlaysId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userid", nullable = false)
    private Users user;

    @ManyToOne
    @MapsId("playlistId")
    @JoinColumn(name = "playlistid", nullable = false)
    private Playlists playlist;

    public PlaysId getId() {
        return id;
    }

    public void setId(PlaysId id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Playlists getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlists playlist) {
        this.playlist = playlist;
    }
}

