package com.example.music_app.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="albums")
public class Album {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	private String nom;
	
	private String image;
		
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@CreationTimestamp
	private LocalDate date_sortie; 
	
	@ManyToMany
	@JoinTable(
	        name = "album_artists",
	        joinColumns = @JoinColumn(name = "album_id"),
	        inverseJoinColumns = @JoinColumn(name = "artist_id")
	    )
	private List<Artist> artists;
	
	@OneToMany(mappedBy = "album")
	private List<Song> songs;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LocalDate getDate_sortie() {
		return date_sortie;
	}

	public void setDate_sortie(LocalDate date_sortie) {
		this.date_sortie = date_sortie;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
}
