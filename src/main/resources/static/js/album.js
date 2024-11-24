function openModalAjouterAlbum() {
    document.getElementById("albumModalAjouterAlbum").style.display = "block";
}

function openModalSupprimerAlbum() {
    document.getElementById("albumModalSupprimerAlbum").style.display = "block";
}

function closeModalAjouterAlbum() {
    document.getElementById("albumModalAjouterAlbum").style.display = "none";
}

function closeModalSupprimerAlbum() {
    document.getElementById("albumModalSupprimerAlbum").style.display = "none";
}


// Fermer la modale si l'utilisateur clique en dehors de la fenêtre
window.onclick = function(event) {
    const modal = document.getElementById("albumModalAjouterAlbum");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
	window.onclick = function(event) {
	    const modal = document.getElementById("albumModalSupprimerAlbum");
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
}
