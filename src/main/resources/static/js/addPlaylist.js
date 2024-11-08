function openModal() {
    document.getElementById("playlistModal").style.display = "block";
}

function closeModal() {
    document.getElementById("playlistModal").style.display = "none";
}

// Fermer la modale si l'utilisateur clique en dehors de la fenêtre
window.onclick = function(event) {
    const modal = document.getElementById("playlistModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
