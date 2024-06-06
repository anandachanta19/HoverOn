package com.msp.hoveron.serviceimpl;

import com.msp.hoveron.entity.Songs;
import com.msp.hoveron.repository.SongRepository;
import com.msp.hoveron.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Songs getSongById(Long songId) {
        return songRepository.findBySongId(songId);
    }

    @Override
    public List<Songs> getAllSongs() {
        return songRepository.findAll();
    }
}