package com.msp.hoveron.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class PlaysId implements Serializable {


    @Column(name = "userid")
    private Long userId;
    @Column(name = "playlistid")
    private Long playlistId;

    // Constructors, getters, setters, and other methods

}

