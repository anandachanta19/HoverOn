document.addEventListener('DOMContentLoaded', function() {
    console.log('Current URL:', window.location.href);
    const urlPath = window.location.pathname;
    const pathSegments = urlPath.split('/');
    const songId = pathSegments[pathSegments.length - 1];
    console.log('Extracted songId:', songId);

    if (!songId) {
        console.error('No song ID provided');
        // Handle the case when songId is missing
        return;
    }

    // Perform actions with the songId, such as fetching song details
    fetchSongDetails(songId);
});

function fetchSongDetails(songId) {
    // Here, you can make a fetch request to fetch song details based on the songId
    // Example:
    fetch('/api/songs/' + songId)
        .then(response => response.json())
        .then(data => {
            // Handle successful response (e.g., display song details)
            console.log('Song details:', data);
            // Initialize the media player with the fetched song details
            initializeMediaPlayer(data);
        })
        .catch(error => {
            console.error('Error fetching song details:', error);
        });
}

// ... rest of your code including the initializeMediaPlayer function ...





function initializeMediaPlayer(song) {
    const audio = new Audio(`/songs/${song.songName}.mp3`);
    const playButton = document.querySelector('.play');
    const volumeButton = document.querySelector('.volume');
    const volumeSliderContainer = document.querySelector('.volume-slider-container');
    const volumeSlider = document.querySelector('.volume-slider');
    const progressBar = document.querySelector('.progress');
    const progressContainer = document.querySelector('.progress-bar');
    const loopButton = document.querySelector('.loop');
    const backButton = document.querySelector('.back');
    const skipBackwardButton = document.querySelector('.skip-backward');
    const skipForwardButton = document.querySelector('.skip-forward');

    updateSongInfo(song);

    let isPlaying = false;
    let isLooping = false;

    playButton.addEventListener('click', function () {
        if (isPlaying) {
            audio.pause();
        } else {
            audio.play();
        }
    });

    audio.addEventListener('play', function () {
        playButton.innerHTML = '<i class="fas fa-pause"></i>';
        isPlaying = true;
    });

    audio.addEventListener('pause', function () {
        playButton.innerHTML = '<i class="fas fa-play"></i>';
        isPlaying = false;
    });

    volumeButton.addEventListener('click', function () {
        volumeSliderContainer.classList.toggle('show');
    });

    volumeSlider.addEventListener('input', function () {
        audio.volume = volumeSlider.value / 100;
        if (audio.volume === 0) {
            volumeButton.innerHTML = '<i class="fas fa-volume-mute"></i>';
        } else {
            volumeButton.innerHTML = '<i class="fas fa-volume-up"></i>';
        }
    });

    loopButton.addEventListener('click', function () {
        isLooping = !isLooping;
        loopButton.classList.toggle('active', isLooping);
        audio.loop = isLooping;
    });

    backButton.addEventListener('click', function () {
        window.location.href = "/hoveron/back";
    });

    skipBackwardButton.addEventListener('click', function () {
        audio.currentTime -= 10;
    });

    skipForwardButton.addEventListener('click', function () {
        audio.currentTime += 10;
    });


    audio.addEventListener('timeupdate', function () {
        const progress = (audio.currentTime / audio.duration) * 100;
        progressBar.style.width = `${progress}%`;

        if (isLooping && audio.currentTime >= audio.duration) {
            audio.currentTime = 0;
            audio.play();
        }
    });

    audio.addEventListener('ended', function () {
        if (!isLooping) {
            playButton.innerHTML = '<i class="fas fa-play"></i>';
            isPlaying = false;
        }
    });

    progressContainer.addEventListener('click', function (e) {
        const width = this.clientWidth;
        const clickX = e.offsetX;
        const duration = audio.duration;

        audio.currentTime = (clickX / width) * duration;
    });
}

function updateSongInfo(song) {
    console.log('Full song object:', song);
    document.querySelector('.song-info img').src = `/img/${song.songName}.jpg`;
    document.querySelector('.song-info h2').textContent = song.songName;
    console.log('Before setting artist name:', document.querySelector('.song-info h1').textContent);
    console.log('Artist name from object:', song.album.artist.artName);
    document.querySelector('.song-info h1').textContent = song.album.artist.artName;
    console.log('After setting artist name:', document.querySelector('.song-info h1').textContent);
}
