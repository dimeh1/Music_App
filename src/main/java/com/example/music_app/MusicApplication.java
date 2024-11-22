package com.example.music_app;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.music_app.model.Album;
import com.example.music_app.model.Artist;
import com.example.music_app.model.Song;
import com.example.music_app.repository.AlbumRepository;
import com.example.music_app.repository.ArtistRepository;
import com.example.music_app.repository.SongRepository;

@SpringBootApplication
public class MusicApplication implements ApplicationRunner{

	public static void main(String[] args) {
		SpringApplication.run(MusicApplication.class, args);
	}
	@Autowired
	ArtistRepository artistRepository;
	@Autowired
	AlbumRepository albumRepository;
	@Autowired
	SongRepository songRepository;
	
	@Override
    public void run(ApplicationArguments args) throws Exception {
        // Ajouter des artistes
        Artist artist1 = new Artist();
        artist1.setName("Me");
        artist1.setImage("artist1.jpg");
        
        Artist artist2 = new Artist();
        artist2.setName("Ma");
        artist2.setImage("artist2.jpg");

        artistRepository.saveAll(Arrays.asList(artist1, artist2));

        // Ajouter des albums
        Album album1 = new Album();
        album1.setNom("Album 1");
        album1.setImage("album1.jpg");
        album1.setDate_sortie(java.time.LocalDate.of(2020, 5, 10));
        album1.setArtists(Arrays.asList(artist1));

        Album album2 = new Album();
        album2.setNom("Album 2");
        album2.setImage("album2.jpg");
        album2.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        album2.setArtists(Arrays.asList(artist2));

        albumRepository.saveAll(Arrays.asList(album1, album2));

        // Ajouter des chansons
        Song song1 = new Song();
        song1.setTitre("Song 1");
        song1.setDuree(180);
        song1.setAlbum(album1);
        song1.setArtists(Arrays.asList(artist1));
        song1.setGenre(com.example.music_app.model.Genre.ROCK); // Exemple d'enum
        song1.setChemin_audio("song1.mp3");

        Song song2 = new Song();
        song2.setTitre("Song 2");
        song2.setDuree(200);
        song2.setAlbum(album2);
        song2.setArtists(Arrays.asList(artist2));
        song2.setGenre(com.example.music_app.model.Genre.POP); // Exemple d'enum
        song2.setChemin_audio("song2.mp3");

        songRepository.saveAll(Arrays.asList(song1, song2));
    }
	
	
}
