document.addEventListener('DOMContentLoaded', function() {
    const searchButton = document.getElementById('searchButton');
    const searchInput = document.getElementById('searchInput');

    searchButton.addEventListener('click', function() {
        const query = searchInput.value.trim();
        if (query !== '') {
            searchSongs(query);
        }
    });
});

function searchSongs(query) {
    fetch(`/hoveron/search/results?query=${query}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json(); // Directly parse the JSON response
        })
        .then(data => {
            displaySearchResults(data);
        })
        .catch(error => {
            console.error('Error searching songs:', error);
        });
}

function displaySearchResults(results) {
    const searchResults = document.getElementById('searchResults');
    searchResults.innerHTML = ''; // Clear previous results
    if (results && results.songs.length > 0) {
        results.songs.forEach(song => {
            const songItem = document.createElement('div');
            songItem.classList.add('song-item');
            songItem.innerHTML = `
                <img src="/img/${song.songName}.jpg" alt="${song.songName}">
                <p>${song.songName}</p>
                <a href="/mediaplayer/${song.songId}" class="play-button">Play</a>
                <button class="add-to-playlist-button" onclick="openPlaylistOverlay(${song.songId})">Add to Playlist</button>
            `;
            searchResults.appendChild(songItem);
        });
    } else {
        searchResults.innerHTML = '<p>No results found</p>';
    }
}

function openPlaylistOverlay(songId) {
    const playlistOverlay = document.getElementById('playlistOverlay');
    // Ensure the overlay is not displayed by default
    playlistOverlay.style.display = 'none';
    if (playlistOverlay) {
        playlistOverlay.style.display = 'flex';

        // Fetch user playlists
        fetch('/hoveron/user/playlists/' + userId)
            .then(response => response.json())
            .then(playlists => {
                const playlistList = playlistOverlay.querySelector('.playlist-list');
                playlistList.innerHTML = '';

                if (playlists.length > 0) {
                    playlists.forEach(playlist => {
                        const playlistItem = document.createElement('div');
                        playlistItem.classList.add('playlist-item');
                        playlistItem.innerHTML = `
                            <p>${playlist.playlistName}</p>
                            <button onclick="addSongToPlaylist(${songId}, ${playlist.playlistId})">Add</button>
                        `;
                        playlistList.appendChild(playlistItem);
                    });
                } else {
                    const noPlaylistsMessage = document.createElement('p');
                    noPlaylistsMessage.textContent = 'No playlists found. Please create a playlist first.';
                    playlistList.appendChild(noPlaylistsMessage);
                }
            })
            .catch(error => {
                console.error('Error fetching playlists:', error);
            });
    }
}

function addSongToPlaylist(songId, playlistId) {
    fetch('/hoveron/playlist/addSong', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ songId, playlistId })
    })
        .then(response => {
            if (response.ok) {
                alert('Song added to playlist successfully!');
                closeOverlay('playlistOverlay');
            } else if (response.status === 409) { // HTTP status code for conflict is 409
                alert('Song is already in the playlist.');
            } else {
                alert('Failed to add song to playlist.');
            }
        })
        .catch(error => {
            console.error('Error adding song to playlist:', error);
            alert('Failed to add song to playlist.');
        });

}

function closeOverlay(overlayId) {
    const overlay = document.getElementById(overlayId);
    if (overlay) {
        overlay.style.display = 'none';
    }
}