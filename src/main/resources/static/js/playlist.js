function redirectToPlaylist(playlistId) {
        window.location.href = '/playlist/' + playlistId;
}

function openModal() {
    const modal = document.getElementById('playlistModal');
    modal.style.display = 'block';
}

function closeModal() {
    const modal = document.getElementById('playlistModal');
    modal.style.display = 'none';
}


function openDeleteModal(playlistId) {
    const modal = document.getElementById('deleteModal');
    const playlistInput = document.getElementById('deletePlaylistId');
    playlistInput.value = playlistId;
    modal.style.display = 'block';
}

function closeDeleteModal() {
    const modal = document.getElementById('deleteModal');
    modal.style.display = 'none';
}

window.addEventListener('click', function(event) {
    const playlistModal = document.getElementById('playlistModal');
    const deleteModal = document.getElementById('deleteModal');
	
    if (event.target === playlistModal) {
        playlistModal.style.display = 'none';
    }

    if (event.target === deleteModal) {
        deleteModal.style.display = 'none';
    }
});


document.querySelectorAll('.delete-button').forEach(button => {
    button.addEventListener('click', (event) => {
        event.stopPropagation();
    });
});
