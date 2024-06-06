package com.msp.hoveron.serviceimpl;

import com.msp.hoveron.entity.*;
import com.msp.hoveron.payload.SongDto;
import com.msp.hoveron.repository.ContainsRepository;
import com.msp.hoveron.repository.PlaylistRepository;
import com.msp.hoveron.repository.PlaysRepository;
import com.msp.hoveron.repository.SongRepository;
import com.msp.hoveron.service.PlaylistService;
import com.msp.hoveron.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final ContainsRepository containsRepository;
    private final SongRepository songRepository;
    private final UsersServiceImpl usersServiceImpl;
    private final SongService songService;
    private final PlaysRepository playsRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, ContainsRepository containsRepository, SongRepository songRepository, UsersServiceImpl usersServiceImpl, SongService songService, PlaysRepository playsRepository) {
        this.playlistRepository = playlistRepository;
        this.containsRepository = containsRepository;
        this.songRepository = songRepository;
        this.usersServiceImpl = usersServiceImpl;
        this.songService = songService;
        this.playsRepository = playsRepository;
    }

    @Override
    public Playlists createPlaylist(String name, Integer userId) {
        // Assuming you have a method in the repository to find the user by ID
        Optional<Users> user = Optional.ofNullable(usersServiceImpl.findByUserId(Long.valueOf(userId)));
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }
        Playlists playlist = new Playlists();
        playlist.setPlaylistName(name);
        playlist.setUser(user.get());
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlists deletePlaylistByPlaylistId(Long playlistId) {
        playlistRepository.deleteById(playlistId);
        return null;
    }

    @Override
    public List<Playlists> getPlaylistsByUserId(Long userId) {
        return playlistRepository.findByUserUserId(userId);
    }

    @Override
    public List<SongDto> getSongsForPlaylist(Long playlistId) {
        List<Contains> containsList = containsRepository.findByIdPlaylistId(playlistId);
        return containsList.stream()
                .map(contains -> {
                    Long songId = contains.getId().getSongId();
                    String songName = songService.getSongById(songId).getSongName();
                    return new SongDto(songName, songId);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void removeSongFromPlaylist(Long songId, Long playlistId) {
        Optional<Playlists> optionalPlaylist = playlistRepository.findById(playlistId);
        if (optionalPlaylist.isPresent()) {
            ContainsId containsId = new ContainsId();
            containsId.setPlaylistId(playlistId);
            containsId.setSongId(songId);
            containsRepository.deleteById(containsId);
        } else {
            throw new RuntimeException("Playlist not found with id: " + playlistId);
        }
    }

    @Override
    public void addSongToPlaylist(Long songId, Long playlistId) {
        Optional<Playlists> optionalPlaylist = playlistRepository.findById(playlistId);
        if (optionalPlaylist.isPresent()) {
            Playlists playlist = optionalPlaylist.get();
            Optional<Songs> optionalSong = songRepository.findById(songId);
            if (optionalSong.isPresent()) {
                Songs song = optionalSong.get();
                Contains contains = new Contains();
                ContainsId containsId = new ContainsId();
                containsId.setPlaylistId(playlistId);
                containsId.setSongId(songId);
                contains.setId(containsId);
                contains.setPlaylist(playlist);
                contains.setSong(song);
                containsRepository.save(contains);
            } else {
                throw new RuntimeException("Song not found with id: " + songId);
            }
        } else {
            throw new RuntimeException("Playlist not found with id: " + playlistId);
        }
    }

    @Override
    public boolean isSongInPlaylist(Long songId, Long playlistId) {
        return containsRepository.existsByIdPlaylistIdAndIdSongId(playlistId, songId);
    }

}

