function openModal() {
    document.getElementById("playlistModal").style.display = "block";
}

function closeModal() {
    document.getElementById("playlistModal").style.display = "none";
}

window.onclick = function(event) {
    const modal = document.getElementById("playlistModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function redirectToPlaylist(playlistId) {
        window.location.href = '/playlist/' + playlistId;
}
