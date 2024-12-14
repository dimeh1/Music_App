function openPlayerModal(songId) {
    fetch(`/api/songs/${songId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch song details');
            }
            return response.json();
        })
        .then(data => {

            // Update song details in the modal
            document.getElementById('song-title').textContent = data.title; // Set song title
            document.getElementById('song-artists').textContent = data.artists.join(', '); // Set artists
            document.getElementById('song-album').textContent = data.album; // Set album name
            // Set and play the audio
            const audioPlayer = document.getElementById('audio-player');
            audioPlayer.src = data.path; // Set audio source
            audioPlayer.play(); // Start playing the song

            // Show the modal
            document.getElementById('player-modal').classList.add('show');
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Could not load song. Please try again later.');
        });
}

function closeModal() {
    const modal = document.getElementById('player-modal');
    modal.classList.remove('show');
    const audioPlayer = document.getElementById('audio-player');
    audioPlayer.pause();
}

// Add click and right-click listeners for playlist
document.addEventListener('DOMContentLoaded', () => {
    const rows = document.querySelectorAll('.clickable-row');

    rows.forEach(row => {
        const songId = row.getAttribute('data-song-id'); // Add `data-song-id` to rows

        row.addEventListener('click', () => openPlayerModal(songId));

        row.addEventListener('contextmenu', (event) => {
            event.preventDefault();
            addToQueue(songId);
        });
    });
});

// Add to playback queue
function addToQueue(songId) {
    const queue = JSON.parse(localStorage.getItem('songQueue')) || [];
    queue.push(songId);
    localStorage.setItem('songQueue', JSON.stringify(queue));
    alert('Song added to queue!');
}