package com.msp.hoveron.service;

import com.msp.hoveron.payload.SearchDto;

public interface SearchService {
    SearchDto searchSongs(String query);
}
