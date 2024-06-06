document.addEventListener('DOMContentLoaded', function() {
    // Call functions that depend on the DOM here
    displayUserPlaylists();
    displaySongs();

    // Other code that needs to run after the DOM is fully loaded
});

function getCsrfToken() {
    // Retrieve the CSRF token from the hidden input
    var csrfTokenElement = document.getElementById('csrfToken');
    if (csrfTokenElement) {
        return csrfTokenElement.value;
    } else {
        console.error('CSRF token element not found.');
        return ''; // Return an empty string or handle the error as needed
    }
}

let songs = [];

fetch('/api/songs/all')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        songs = data; // Assuming the backend returns an array of songs
        displaySongs(); // Call displaySongs here, after the songs have been fetched
    })
    .catch(error => {
        console.error('Error fetching songs:', error);
    });

function displaySongs() {
    songs = shuffleArray(songs); // Randomize the order of songs
    const songListDiv = document.querySelector('.song-list');
    songListDiv.innerHTML = ''; // Clear existing songs

    songs.forEach(song => {
        const songDiv = document.createElement('div');
        songDiv.className = 'song';
        songDiv.innerHTML = `
            <img src="/img/${song.songName}.jpg" alt="Song Image">
            <p><b>${song.songName}</b></p>
            <button onclick="playSong(${song.songId})">Play</button>
        `;
        songListDiv.appendChild(songDiv);
    });
}

// Fisher-Yates (aka Knuth) Shuffle
function shuffleArray(array) {
    let currentIndex = array.length, temporaryValue, randomIndex;

    // While there remain elements to shuffle...
    while (0 !== currentIndex) {
        // Pick a remaining element...
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;

        // And swap it with the current element.
        temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
    }

    return array;
}

function toggleMenu() {
    var menuIcon = document.querySelector('.menu-icon');
    menuIcon.classList.toggle('active');

    var menuOverlay = document.getElementById('menuOverlay');
    menuOverlay.style.display = (menuOverlay.style.display === 'block') ? 'none' : 'block';
}

function navigateBack() {
    var menuIcon = document.querySelector('.menu-icon');
    menuIcon.classList.remove('active');

    var menuOverlay = document.getElementById('menuOverlay');
    menuOverlay.style.display = 'none';
}


function openOverlay(overlayId) {
    var overlay = document.getElementById(overlayId);
    if (overlay) {
        // Check if it's the profile overlay
        if (overlayId === 'profileOverlay') {
            // Fetch user details and update the overlay
            fetch('/hoveron/user/details')
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to fetch user details');
                    }
                    return response.json();
                })
                .then(userDetails => {
                    // Update the profile overlay with user details
                    var profileContent = `
                        <h3>Profile</h3>
                        <p>Username: ${userDetails.username}</p>
                        <p>Email: ${userDetails.email}</p>
                        <p>Gender: ${userDetails.gender}</p>
                        <p>Age: ${userDetails.age}</p>
                        <button onclick="closeOverlay('profileOverlay')">Back</button>
                    `;
                    document.getElementById('profileOverlay').querySelector('.overlay-content').innerHTML = profileContent;
                    overlay.style.display = "block";
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        } else {
            // For other overlays, just display them
            overlay.style.display = "block";
        }
    }
}


function closeOverlay(overlayId) {
    var overlay = document.getElementById(overlayId);

    if (overlay) {
        overlay.style.display = "none";
    }
}

function createPlaylist() {
    var playlistNameInput = document.getElementById('playlistNameInput');
    if (!playlistNameInput) {
        console.error('Playlist name input field not found.');
        return;
    }
    var playlistName = playlistNameInput.value.trim();
    if (playlistName === '') {
        alert('Playlist name cannot be empty.');
        return;
    }

    var requestBody = {
        playlistName: playlistName,
        userId: userId
    };

    fetch('/hoveron/createPlaylist', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok.');
            }
            return response.json();
        })
        .then(savedPlaylist => {
            alert('Playlist created successfully with ID: ' + savedPlaylist.playlistId);
            window.location.href = '/hoveron/home/' + userId;
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });
}


// Add this function to fetch and display playlists
function displayUserPlaylists() {
    fetch('/hoveron/user/playlists/' + userId)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch playlists');
            }
            return response.json();
        })
        .then(playlists => {
            playlists.forEach(playlist => {
                console.log(playlist);
            });
            var playlistContainer = document.querySelector('.playlists .playlist-icons');
            playlistContainer.innerHTML = ''; // Clear existing content
            playlists.forEach(playlist => {
                var playlistElement = `
                    <div class="playlist">
                        <p>${playlist.playlistName}</p>
                        <button onclick="openPlaylist(${playlist.playlistId})">Open</button>
                    </div>
                `;
                playlistContainer.innerHTML += playlistElement;
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function openPlaylist(playlistId) {
    console.log('openPlaylist called with playlistId:', playlistId);
    fetch('/hoveron/playlist/songs/' + playlistId)
        .then(response => {
            console.log('Received response for playlistId:', playlistId);
            return response.json();
        })
        .then(songs => {
            console.log('Songs data:', songs);
            var playlistOverlay = document.getElementById('playlistOverlay');
            if (!playlistOverlay) {
                console.error('playlistOverlay element not found!');
                return;
            }
            var playlistContent = playlistOverlay.querySelector('.overlay-content');
            if (!playlistContent) {
                console.error('overlay-content element not found!');
                return;
            }
            playlistContent.innerHTML = ''; // Clear existing content

            var songListHtml = '<h3>Songs</h3><div class="song-list">';

            songs.forEach(song => {
                songListHtml += `
                    <div class="song">
                        <img src="/img/${song.songName}.jpg" alt="${song.songName}">
                        <p><b>${song.songName}</b></p>
                        <button onclick="playSong(${song.songId})">Play</button>
                        <button onclick="removeSong(${song.songId}, ${playlistId})">Remove</button>
                    </div>
                `;
            });

            songListHtml += '</div>';
            songListHtml += '<button onclick="closeOverlay(\'playlistOverlay\')">Close</button>';
            playlistContent.innerHTML = songListHtml;
            playlistOverlay.style.display = 'block';
        })
        .catch(error => {
            console.error('Error fetching songs:', error);
        });
}

// Update playSong to redirect to the MediaPlayerController
function playSong(songId) {
    window.location.href = '/mediaplayer/' + songId;
}

// Implement removeSong according to your application logic
function removeSong(songId, playlistId) {
    fetch('/hoveron/playlist/removeSong', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ songId, playlistId })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to remove song from playlist');
            }
            alert('Song removed from playlist successfully!');
            openPlaylist(playlistId); // Refresh the playlist
        })
        .catch(error => {
            console.error('Error removing song:', error);
            alert('Failed to remove song from playlist.');
        });
}


function confirmLogout() {
    // Show a confirmation dialog
    if (confirm('Are you sure you want to log out?')) {
        // If the user confirms, perform the logout
        logout();
    } else {
        // If the user cancels, close the menu overlay
        closeOverlay('menuOverlay');
    }
}

function logout() {
    fetch('/hoveron/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        // Add any necessary CSRF tokens or other security measures
    }).then(response => {
        // Redirect to the login page after successful logout
        window.location.href = '/hoveron/login';
    }).catch(error => {
        console.error('Logout failed:', error);
    });
}



// Function to update profile information
// Function to update profile information
// Function to update profile information
function updateProfile() {
    var username = document.getElementById('usernameInput').value;
    var email = document.getElementById('emailInput').value;
    var gender = document.getElementById('genderInput').value;
    var age = document.getElementById('ageInput').value;

    // Object to hold the updates
    var updates = {};

    // Add fields to the updates object only if they have been changed
    if (username.trim()) updates.username = username;
    if (email.trim()) updates.email = email;
    if (gender.trim()) updates.gender = gender;
    if (age.trim()) updates.age = age;

    console.log('Updates:', updates); // Log updates

    const csrfToken = getCsrfToken();

    // Make an API call to the server to update the user's profile with the changes
    fetch('/hoveron/update-profile', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-Token': csrfToken
            // Include CSRF token as needed
        },
        body: JSON.stringify(updates)
    }).then(response => {
        if (response.ok) {
            alert('Profile updated successfully!');
            closeOverlay('updateProfileOverlay');
        } else {
            alert('Failed to update profile.');
        }
    }).catch(error => {
        console.error('Error updating profile:', error);
    });
}


// Function to update password
function updatePassword() {
    var oldPassword = document.getElementById('oldPasswordInput').value;
    var newPassword = document.getElementById('newPasswordInput').value;
    var confirmNewPassword = document.getElementById('confirmNewPasswordInput').value;

    // Check if new passwords match
    if (newPassword !== confirmNewPassword) {
        alert('New passwords do not match.');
        return;
    }

    const csrfToken = getCsrfToken();
    // Make an API call to the server to update the user's password
    fetch('/hoveron/update-password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-Token': csrfToken
            // Include CSRF token as needed
        },
        body: JSON.stringify({
            oldPassword: oldPassword,
            newPassword: newPassword
        })
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Password updated successfully!');
                closeOverlay('updateProfileOverlay');
                closeOverlay('profileOverlay');
            } else {
                alert('Incorrect old password'); // "Incorrect old password" or other messages from the server
            }
        }).catch(error => {
        console.error('Error updating password:', error);
    });
}





