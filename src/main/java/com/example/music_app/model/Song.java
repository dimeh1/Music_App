package com.example.music_app.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name ="songs")
public class Song {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String titre;
	
	private int duree;
	
	@CreationTimestamp
	private LocalDate date_sortie;
	
	@ManyToOne
	private Album album;
	
	@ManyToMany
	@JoinTable(
	        name = "song_artists",
	        joinColumns = @JoinColumn(name = "song_id"),
	        inverseJoinColumns = @JoinColumn(name = "artist_id")
	    )
	private List<Artist> artists;
	
	@ManyToMany(mappedBy = "songs")
	private List<Playlist> playlists;
	
	@OneToMany(mappedBy ="song")
	private List<Like> likes;
	
	@Enumerated(EnumType.STRING)
	private Genre genre;
	
	private String chemin_audio;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public LocalDate getDate_sortie() {
		return date_sortie;
	}

	public void setDate_sortie(LocalDate date_sortie) {
		this.date_sortie = date_sortie;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getChemin_audio() {
		return chemin_audio;
	}

	public void setChemin_audio(String chemin_audio) {
		this.chemin_audio = chemin_audio;
	}
	
}