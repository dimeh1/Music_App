package com.example.music_app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.music_app.model.Playlist;
import com.example.music_app.model.Song;
import com.example.music_app.model.User;
import com.example.music_app.repository.PlaylistRepository;
import com.example.music_app.repository.SongRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PlaylistController {
	
	@Autowired
	PlaylistRepository playlistRepository;
	@Autowired
	SongRepository songRepository;
	
	@PostMapping("/create-playlist")
	public String createPLaylist(Model model,@RequestParam("name") String name, HttpSession session ) {
		if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
			User user = (User) session.getAttribute("user");
			
			Playlist playlist = new Playlist();
			playlist.setName(name);
			playlist.setUser(user);
			
			playlistRepository.save(playlist);
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/update-playlist")
	public String updatePlaylist(Model model, @RequestParam("playlistId") Long playlistId, @RequestParam("name") String name, HttpSession session) {
		if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
			User user = (User) session.getAttribute("user");
			
			Playlist playlist = playlistRepository.findByIdAndUser(playlistId, user);
			
			if (playlist != null) {
				playlist.setName(name);
				
				playlistRepository.save(playlist);
				model.addAttribute("success","Le nom de la playlist à été modifié avec succès.");
			}
			else {
				model.addAttribute("error", "Playlist non trouvé.");
			}
			
		}
		else {
			model.addAttribute("error", "Vous devez être connecté pour modifier le nom d'un playlist.");
		}
		return "playlist";
	}
	
	@PostMapping("/add-like")
	public String addSongInLike(Model model, @RequestParam("songId") Long songId, HttpSession session) {
	    if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
	        
	        User user = (User) session.getAttribute("user");
	        
	        Optional<Song> songOptional = songRepository.findById(songId);
	        
	        if (songOptional.isPresent()) {
	            Song song = songOptional.get();
	            
	            Playlist likePlaylist = playlistRepository.findByIdAndUser(1L, user);
	            
	            if (likePlaylist != null) {
	                likePlaylist.getSongs().add(song);
	                
	                playlistRepository.save(likePlaylist);
	                songRepository.save(song);
	                
	                model.addAttribute("success", "La chanson a été ajoutée à votre playlist Like.");
	            } else {
	                model.addAttribute("error", "Playlist 'Like' introuvable.");
	            }
	        } else {
	            model.addAttribute("error", "Chanson non trouvée.");
	        }
	    } else {
	        model.addAttribute("error", "Vous devez être connecté pour ajouter une chanson à votre playlist.");
	    }
	    return "playlist";
	}

}
