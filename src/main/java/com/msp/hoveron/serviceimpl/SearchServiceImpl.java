package com.msp.hoveron.serviceimpl;

import com.msp.hoveron.entity.*;
import com.msp.hoveron.payload.SearchDto;
import com.msp.hoveron.repository.*;
import com.msp.hoveron.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;
    private final SongArtistsRepository songArtistRepository;
    private final SongGenresRepository songGenreRepository;

    public SearchServiceImpl(SongRepository songRepository, AlbumRepository albumRepository,
                             ArtistRepository artistRepository, GenreRepository genreRepository,
                             SongArtistsRepository songArtistRepository, SongGenresRepository songGenreRepository) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.genreRepository = genreRepository;
        this.songArtistRepository = songArtistRepository;
        this.songGenreRepository = songGenreRepository;
    }

    @Override
    public SearchDto searchSongs(String query) {
        List<Songs> songResults = songRepository.findBySongNameContainingIgnoreCase(query);
        List<Albums> albumResults = albumRepository.findByAlbNameContainingIgnoreCase(query);
        List<Artists> artistResults = artistRepository.findByArtNameContainingIgnoreCase(query);
        List<Genres> genreResults = genreRepository.findByGenreNameContainingIgnoreCase(query);

        // Initialize lists to store image URLs for each song
        List<String> imageUrls = songResults.stream()
                .map(song -> String.format("/img/%s.jpg", song.getSongName()))
                .collect(Collectors.toList());

        List<Integer> songIdsForArtist = artistResults.stream()
                .flatMap(artist -> songArtistRepository.findSongartistsByArtist_ArtistId(artist.getArtistId())
                        .stream().map(SongArtistsId -> SongArtistsId.getId().getSongId()))
                .collect(Collectors.toList());

        List<Integer> songIdsForGenre = genreResults.stream()
                .flatMap(genre -> songGenreRepository.findSonggenresByGenre_GenreId(genre.getGenreId())
                        .stream().map(SongGenresId -> SongGenresId.getId().getSongId()))
                .collect(Collectors.toList());

        // Ensure that each song has an associated image URL
        while (imageUrls.size() < songResults.size()) {
            imageUrls.add("/img/default.jpg"); // Add default image URL if not available for a song
        }

        return new SearchDto(songResults, albumResults, artistResults, genreResults, songIdsForArtist,
                songIdsForGenre, imageUrls);
    }

}