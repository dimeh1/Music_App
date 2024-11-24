package com.example.music_app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	    if (loggedIn != null && loggedIn) {  //rajouter la vérification du statut "ADMIN" pour acceder à admin.html
	    	User userSession = (User) session.getAttribute("user");
	    	User user = userRepository.findByEmail(userSession.getEmail()).get();
	    	
	        model.addAttribute("user", user);
	        model.addAttribute("playlists", user.getPlaylists());
	    } else {
	    	model.addAttribute("user",null);
	    }
	    return "admin";
	}
	

    @PostMapping("/ajouterArtiste")
    public String ajouterArtiste(Model model, @RequestParam("name") String name) {
    	Artist artiste = new Artist();
    	artiste.setName(name);
    	artiste.setAlbums(new ArrayList<Album>());
    	artiste.setSongs(new ArrayList<Song>());
        artistRepository.save(artiste);
        return "redirect:/admin";
    }
    
 
    
    
    @DeleteMapping("/supprimerArtiste") 
    public String supprimerArtiste(Model model, @RequestParam("name") String name) {
        Artist artist = artistRepository.findByName(name);
        if (artist != null) {
        	artistRepository.delete(artist);
        	//il faut aussi supprimer les références dans les autres tables
        }
        else {
        	model.addAttribute("error", "l'artiste saisit n'existe pas");
        }
        return "redirect:/admin";
    }
    
    
   

    @PostMapping("/ajouterAlbum")
    public String ajouterAlbum(Model model,@RequestParam("name_album") String nameAlbum, @RequestParam("name_artists") String nameArtists,@RequestParam("name_songs") String nameSongs, @RequestParam("image") String image,RedirectAttributes redirectAttributes) { //à voir pour le type de image
    	try {
    	Album album =new Album();
    	album.setNom(nameAlbum);	//on remplit les paramètres de l'album
    	album.setImage(image); //voir pour le type de image à l'affichage
    	albumRepository.save(album);
    	
    	//////////////////////////////////////////////////////////////////////////////////////////////////
        List<String> artistNames = Arrays.stream(nameArtists.split(",")) // Pour découper la chaîne en une liste de noms d'artistes
                .map(String::trim) // Supprimer les espaces inutiles
                .filter(name -> !name.isEmpty()) // Filtrer les noms vides
                .collect(Collectors.toList());
        
        List<Artist> artists = new ArrayList<>();
        
        for (String artistName : artistNames) {
            Artist artist = artistRepository.findByName(artistName); //On vérifie que l'artiste existe déjà

            if (artist == null) {	//Si l'artiste n'existe pas on en crée un nouveau
                artist = new Artist();
                artist.setName(artistName);
                artist.setAlbums(new ArrayList<Album>());
            	artist.setSongs(new ArrayList<Song>());
                artistRepository.save(artist); 
            }

            artists.add(artist); //on l'ajoute à la liste des artistes de l'album
        }
        album.setArtists(artists); 
        

        for (Artist artist : artists) { //On ajoute l'album à chaque artiste
            artist.getAlbums().add(album);
            artistRepository.save(artist);
        }
        
     // Gestion des musiques
        List<String> songNames = Arrays.stream(nameSongs.split(","))
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .collect(Collectors.toList());

        List<Song> songs = new ArrayList<>();
        
        for (String songName : songNames) {
            Song sg= songRepository.findByTitre(songName);
            
            if(sg==null) { //Si la musique n'a pas été crée on la crée
            	sg = new Song();
                sg.setTitre(songName);
                sg.setAlbum(album);
            }
            songRepository.save(sg);
            songs.add(sg); //On l'ajoute à la liste des musiques de l'album
            
        }
        //ajouter la musique à la liste des musiques de chaque artiste
        album.setSongs(songs);
        
        albumRepository.save(album);
        
        redirectAttributes.addFlashAttribute("success", "Album ajouté avec succès !");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout de l'album.");
    }

    return "redirect:/admin";
}

    @GetMapping("/supprimerAlbum/{id}")
    public String supprimerAlbum(@PathVariable Long id) {
    //	albumRepository.delete(id);
        return "redirect:/admin";
    }
    
    

    @PostMapping("/ajouterChanson")
    public String ajouterChanson(Model model,@RequestParam("titre") String titre ,@RequestParam("name_artists") String nameArtists,@RequestParam("duree") int duree, @RequestParam("image") String image, RedirectAttributes redirectAttributes) {
    	try {
    		
    	Song chanson=new Song();
    	chanson.setTitre(titre);
        chanson.setDuree(duree);
        chanson.setImage(image);
        songRepository.save(chanson);
        
        List<String> artistNames = Arrays.stream(nameArtists.split(",")) // Pour découper la chaîne en une liste de noms d'artistes
                .map(String::trim) // Supprimer les espaces inutiles
                .filter(name -> !name.isEmpty()) // Filtrer les noms vides
                .collect(Collectors.toList());
        
        List<Artist> artists = new ArrayList<>();
        
        for (String artistName : artistNames) {
            Artist artist = artistRepository.findByName(artistName); //On vérifie que l'artiste existe déjà

            if (artist == null) {	//Si l'artiste n'existe pas on en crée un nouveau
                artist = new Artist();
                artist.setName(artistName);
                artist.setAlbums(new ArrayList<Album>());
            	artist.setSongs(new ArrayList<Song>());
                 
            }
            artist.getSongs().add(chanson);
            artistRepository.save(artist);
            
            artists.add(artist); //on l'ajoute à la liste des artistes de la musique
        }
        chanson.setArtists(artists);
        
        songRepository.save(chanson);
        
        redirectAttributes.addFlashAttribute("success", "musique ajoutée avec succès !");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout de la musique");
    }
        return "redirect:/admin";
    }

    @GetMapping("/supprimerChanson/{id}")
    public String supprimerChanson(@PathVariable Long id) {
    //	songRepository.delete(id);
        return "redirect:/admin";
    }
    
    
    

}
