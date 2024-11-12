package com.example.music_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
public class AdminController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;

	
	@GetMapping("/admin")
	public String admin(Model model, HttpSession session) {
	    Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (loggedIn != null && loggedIn) {
	    	User userSession = (User) session.getAttribute("user");
	    	User user = userRepository.findByEmail(userSession.getEmail()).get();
	    	
	        model.addAttribute("user", user);
	        model.addAttribute("playlists", user.getPlaylists());
	    } else {
	    	model.addAttribute("user",null);
	    }
	    return "admin";
	}
	
	@GetMapping("/ajouterArtiste")
    public String ajouterArtisteForm(Model model) {
        model.addAttribute("artiste", new Artist());
        return "ajouterArtiste";
    }

    @PostMapping("/ajouterArtiste")
    public String ajouterArtiste(@ModelAttribute("artiste") Artist artiste) {
        artistRepository.save(artiste);
        return "redirect:/admin";
    }

    @GetMapping("/supprimerArtiste/{id}")
    public String supprimerArtiste(@PathVariable Long id) {
        artistRepository.deleteById(id);
        return "redirect:/admin";
    }
    
    
    
    @GetMapping("/ajouterAlbum")
    public String ajouterAlbumForm(Model model) {
        model.addAttribute("album", new Album());
        return "ajouterAlbum";
    }

    @PostMapping("/ajouterAlbum")
    public String ajouterAlbum(@ModelAttribute("album") Album album) {
        albumRepository.save(album);
        return "redirect:/admin";
    }

    @GetMapping("/supprimerAlbum/{id}")
    public String supprimerAlbum(@PathVariable Long id) {
    //	albumRepository.delete(id);
        return "redirect:/admin";
    }
    
    
    
    @GetMapping("/ajouterChanson")
    public String ajouterChansonForm(Model model) {
        model.addAttribute("chanson", new Song());
        return "ajouterChanson";
    }

    @PostMapping("/ajouterChanson")
    public String ajouterChanson(@ModelAttribute("chanson") Song chanson) {
        songRepository.save(chanson);
        return "redirect:/admin";
    }

    @GetMapping("/supprimerChanson/{id}")
    public String supprimerChanson(@PathVariable Long id) {
    //	songRepository.delete(id);
        return "redirect:/admin";
    }
    
    
    

}
