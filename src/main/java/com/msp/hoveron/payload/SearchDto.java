package com.msp.hoveron.payload;

import com.msp.hoveron.entity.Albums;
import com.msp.hoveron.entity.Artists;
import com.msp.hoveron.entity.Genres;
import com.msp.hoveron.entity.Songs;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchDto {
    private List<Songs> songs;
    private List<Albums> albums;
    private List<Artists> artists;
    private List<Genres> genres;
    private List<Integer> songIdsForArtist;
    private List<Integer> songIdsForGenre;
    private List<String> imageUrls;

    public SearchDto(List<Songs> songs, List<Albums> albums, List<Artists> artists, List<Genres> genres,
                     List<Integer> songIdsForArtist, List<Integer> songIdsForGenre, List<String> imageUrls) {
        this.songs = songs;
        this.albums = albums;
        this.artists = artists;
        this.genres = genres;
        this.songIdsForArtist = songIdsForArtist;
        this.songIdsForGenre = songIdsForGenre;
        this.imageUrls = imageUrls;
    }

    public SearchDto() {

    }

    public List<Songs> getSongResults() {
        return songs;
    }
}
