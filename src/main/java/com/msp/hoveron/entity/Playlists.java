package com.msp.hoveron.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "playlists")
public class Playlists implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlistid")
    private Long playlistId;

    @Column(name = "playlistname", nullable = false)
    private String playlistName;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private Users user;

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }
    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
