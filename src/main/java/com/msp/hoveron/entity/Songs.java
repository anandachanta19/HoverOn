package com.msp.hoveron.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "songs")
public class Songs implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "songid")
    private Long songId;

    @Column(name = "songname", nullable = false)
    private String songName;

    @ManyToOne
    @JoinColumn(name = "albumid", nullable = false)
    private Albums album;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "location", nullable = false)
    private String location;

}
