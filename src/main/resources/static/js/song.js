function openModalAjouterSong() {
    document.getElementById("songModalAjouterSong").style.display = "block";
}

function openModalSupprimerSong() {
    document.getElementById("songModalSupprimerSong").style.display = "block";
}

function closeModalAjouterSong() {
    document.getElementById("songModalAjouterSong").style.display = "none";
}

function closeModalSupprimerSong() {
    document.getElementById("songModalSupprimerSong").style.display = "none";
}


// Fermer la modale si l'utilisateur clique en dehors de la fenêtre
window.onclick = function(event) {
    const modal = document.getElementById("songModalAjouterSong");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
	window.onclick = function(event) {
	    const modal = document.getElementById("songModalSupprimerSong");
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
}
