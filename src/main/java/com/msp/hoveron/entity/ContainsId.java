package com.msp.hoveron.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class ContainsId implements Serializable {

    @Column(name = "playlistid")
    private Long playlistId;
    @Column(name = "songid")
    private Long songId;

    // Constructors, getters, setters, and other methods
}

