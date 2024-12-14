package com.example.music_app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.music_app.model.Song;
import com.example.music_app.model.User;
import com.example.music_app.repository.PlaylistRepository;
import com.example.music_app.repository.SongRepository;
import com.example.music_app.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class SongController {

	@Autowired
	PlaylistRepository playlistRepository;
	@Autowired
	SongRepository songRepository;
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/song/{id}")
	public String viewSong(@PathVariable("id") Long songId, Model model, HttpSession session) {
	    if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
	        User user = (User) session.getAttribute("user");

	        Optional<Song> songOptional = songRepository.findById(songId);
	        if (songOptional.isPresent()) {
	            Song song = songOptional.get();
	            User userSession = userRepository.findByEmail(user.getEmail()).get();
	            
	            model.addAttribute("user", userSession);
	            model.addAttribute("playlists", userSession.getPlaylists());
	            model.addAttribute("song", song);
	            model.addAttribute("artists", song.getArtists());
	            model.addAttribute("album", song.getAlbum().getNom());
				model.addAttribute("chemin_audio", song.getChemin_audio());
	            return "song";
	        } else {
	            model.addAttribute("error", "Musique introuvable ou non autorisée.");
	        }
	    } else {
	        model.addAttribute("error", "Vous devez être connecté pour voir une musique.");
	        return "redirect:/login";
	    }
	    return "redirect:/";
	}
}
