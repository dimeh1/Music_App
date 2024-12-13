package com.example.music_app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.music_app.model.Album;
import com.example.music_app.model.User;
import com.example.music_app.repository.AlbumRepository;
import com.example.music_app.repository.PlaylistRepository;
import com.example.music_app.repository.SongRepository;
import com.example.music_app.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AlbumController {
	
	@Autowired
	PlaylistRepository playlistRepository;
	@Autowired
	SongRepository songRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AlbumRepository albumRepository;
	
	@GetMapping("/album/{id}")
	public String viewAlbum(@PathVariable("id") Long albumId, Model model, HttpSession session) {
	    if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
	        User user = (User) session.getAttribute("user");

	        Optional<Album> albumOptional = albumRepository.findById(albumId);
	        if (albumOptional.isPresent()) {
	            Album album = albumOptional.get();
	            User userSession = userRepository.findByEmail(user.getEmail()).get();
	            
	            model.addAttribute("user", userSession);
	            model.addAttribute("playlists", userSession.getPlaylists());
	            model.addAttribute("album", album);
	            return "album";
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
