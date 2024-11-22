package com.example.music_app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.music_app.model.Album;
import com.example.music_app.model.Artist;
import com.example.music_app.model.Song;
import com.example.music_app.model.User;
import com.example.music_app.repository.AlbumRepository;
import com.example.music_app.repository.ArtistRepository;
import com.example.music_app.repository.SongRepository;
import com.example.music_app.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	ArtistRepository artistRepository;
	@Autowired
	AlbumRepository albumRepository;
	@Autowired
	SongRepository songRepository;
	
	@GetMapping("/")
	public String home(Model model, HttpSession session) {
	    Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (loggedIn != null && loggedIn) {
	        User userSession = (User) session.getAttribute("user");
	        User user = userRepository.findByEmail(userSession.getEmail()).get();

	        model.addAttribute("user", user);
	        model.addAttribute("playlists", user.getPlaylists());
	    } else {
	        model.addAttribute("user", null);
	    }

	    // Récupérer tous les artistes, albums et chansons
	    List<Artist> artists = artistRepository.findAll();
	    List<Album> albums = albumRepository.findAll();
	    List<Song> songs = songRepository.findAll();

	    // Limiter le nombre d'éléments à afficher (par exemple 5)
	    List<Artist> limitedArtists = artists.stream().limit(5).collect(Collectors.toList());
	    List<Album> limitedAlbums = albums.stream().limit(5).collect(Collectors.toList());
	    List<Song> limitedSongs = songs.stream().limit(5).collect(Collectors.toList());

	    // Ajouter ces éléments au modèle
	    model.addAttribute("artists", limitedArtists);
	    model.addAttribute("albums", limitedAlbums);
	    model.addAttribute("songs", limitedSongs);

	    return "home";
	}


}
