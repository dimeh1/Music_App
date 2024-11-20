function openModalAjouterArtist() {
    document.getElementById("artistModalAjouterArtist").style.display = "block";
}

function openModalSupprimerArtist() {
    document.getElementById("artistModalSupprimerArtist").style.display = "block";
}

function closeModalAjouterArtist() {
    document.getElementById("artistModalAjouterArtist").style.display = "none";
}

function closeModalSupprimerArtist() {
    document.getElementById("artistModalSupprimerArtist").style.display = "none";
}


// Fermer la modale si l'utilisateur clique en dehors de la fenêtre
window.onclick = function(event) {
    const modal = document.getElementById("artistModalAjouterArtist");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
	window.onclick = function(event) {
	    const modal = document.getElementById("artistModalSupprimerArtist");
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
}
