function openModal() {
    document.getElementById("artistModal").style.display = "block";
}

function closeModal() {
    document.getElementById("artistModal").style.display = "none";
}

// Fermer la modale si l'utilisateur clique en dehors de la fenêtre
window.onclick = function(event) {
    const modal = document.getElementById("artistModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
