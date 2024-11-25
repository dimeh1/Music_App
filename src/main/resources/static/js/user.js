function openModalSupprimerUser() {
    document.getElementById("userModalSupprimerUser").style.display = "block";
}

function closeModalSupprimerUser() {
    document.getElementById("userModalSupprimerUser").style.display = "none";
}


// Fermer la modale si l'utilisateur clique en dehors de la fenêtre
window.onclick = function(event) {
	const modal = document.getElementById("userModalSupprimerUser");
	if (event.target == modal) {
		modal.style.display = "none";
	}
}
