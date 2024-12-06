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
	const updateModal = document.getElementById('updatePlaylistModal');
	
    if (event.target === playlistModal) {
        playlistModal.style.display = 'none';
    }

    if (event.target === deleteModal) {
        deleteModal.style.display = 'none';
    }
	
	if (event.target === updateModal) {
        updateModal.style.display = "none";
	}
});


document.querySelectorAll('.delete-button').forEach(button => {
    button.addEventListener('click', (event) => {
        event.stopPropagation();
    });
});

function openUpdateModal(playlistId, currentName) {
    // Récupérer l'élément du modal
    const modal = document.getElementById('updatePlaylistModal');
    
    const updateNameInput = document.getElementById('updateName');
    const playlistIdInput = document.getElementById('updatePlaylistId');

    // Mettre à jour le formulaire avec les informations de la playlist
    playlistIdInput.value = playlistId;  // Remplir le champ caché avec l'ID de la playlist
    updateNameInput.value = currentName;  // Mettre le nom actuel dans le champ du formulaire

    // Afficher le modal
    modal.style.display = 'block';

}

// Fonction pour fermer le modal
function closeUpdateModal() {
    const modal = document.getElementById('updatePlaylistModal');
    modal.style.display = 'none';  // Cacher le modal
}