package com.example.music_app.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.music_app.model.Album;
import com.example.music_app.model.Artist;
import com.example.music_app.model.Song;
import com.example.music_app.model.Status;
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
	    	
	    	//On vérifie que le user est bien un compte admin
	    	if(user.getStatus().equals(Status.ADMIN)) {
	    		model.addAttribute("user", user);
		        model.addAttribute("playlists", user.getPlaylists());
		        return "admin";
	    	}
	    	else {
	    		return "redirect:/";
	    	}
	    } 
	    else {
	    	model.addAttribute("user",null);
	    }
	    return "redirect:/";
	}
	

	@PostMapping("/ajouterArtiste")
	public String ajouterArtiste(Model model, @RequestParam("name") String name, @RequestParam("image") String imageUrl) {
	    Artist artiste = new Artist();
	    artiste.setName(name);

	    //L'URL de l'image est directement récupérée depuis le formulaire
	    artiste.setImage(imageUrl);

	    
	    artiste.setAlbums(new ArrayList<>());
	    artiste.setSongs(new ArrayList<>());

	    
	    artistRepository.save(artiste);

	    return "redirect:/admin";
	}


    
    @DeleteMapping("/supprimerArtiste") 
    public String supprimerArtiste(Model model, @RequestParam("name") String name) {
        Artist artist = artistRepository.findByName(name);
        if (artist != null) {
        	artistRepository.delete(artist);
        }
        else {
        	model.addAttribute("error", "l'artiste saisit n'existe pas");
        }
        return "redirect:/admin";
    }
    
    
   

    @PostMapping("/ajouterAlbum")
    public String ajouterAlbum(Model model,@RequestParam("name_album") String nameAlbum, @RequestParam("name_artists") String nameArtists,@RequestParam("name_songs") String nameSongs, @RequestParam("image") String imageUrl,RedirectAttributes redirectAttributes) { //à voir pour le type de image
    	try {
    	Album album =new Album();
    	album.setNom(nameAlbum);	//on remplit les paramètres de l'album
    	album.setImage(imageUrl); //voir pour le type de image à l'affichage
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
                artist.setImage(imageUrl);
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
                sg.setArtists(new ArrayList<>());
                sg.setImage(imageUrl);
            }
            songRepository.save(sg);
            songs.add(sg); //On l'ajoute à la liste des musiques de l'album
                           
        }
      //Pour ajouter chaque musique à la liste des musiques de chaque artiste et chaque artiste à la liste des artiste de la chanson (ne marche pas)
        for(Artist ar : artists) {
        	for(Song song : songs) {
        		ar.getSongs().add(song);
            	artistRepository.save(ar);
            	song.getArtists().add(ar);
            	songRepository.save(song);
        	}
        	
        }

        album.setSongs(songs);
        
        albumRepository.save(album);
        
        redirectAttributes.addFlashAttribute("success", "Album ajouté avec succès !");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout de l'album.");
    }

    return "redirect:/admin";
}

    @DeleteMapping("/supprimerAlbum")
    public String supprimerAlbum(Model model, @RequestParam("name") String name) {
    	Album album= albumRepository.findByNom(name);
    	if (album != null) {
    		for(Song song : album.getSongs()) {
    			songRepository.delete(song);
    		}
    		albumRepository.delete(album);
    	}
    	else {
    		model.addAttribute("error, l'album saisit n'est pas valide");
    	}
        return "redirect:/admin";
    }




        @PostMapping("/ajouterChanson")
        public String ajouterChanson(Model model,
                                     @RequestParam("titre") String titre,
                                     @RequestParam("name_artists") String nameArtists,
                                     @RequestParam("image") String imageUrl,
                                     @RequestParam("duree") int duree,
                                     @RequestParam("file") MultipartFile file,
                                     RedirectAttributes redirectAttributes) {

            String fileName = file.getOriginalFilename();
            fileName = fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
            String uploadDir = "src/main/resources/static/audio/";

            try {
                // Step 1: Check if the song already exists
                Song existingSong = songRepository.findByTitre(titre);
                if (existingSong != null) {
                    redirectAttributes.addFlashAttribute("error", "A song with this title already exists!");
                    return "redirect:/admin";
                }

                // Step 2: Handle album reuse or creation
                Album album = albumRepository.findByNom(titre); // Try to find an existing album with the same name
                if (album == null) {
                    album = new Album();
                    album.setNom(titre);
                    album.setImage(imageUrl);
                    album.setArtists(new ArrayList<>()); // Initialize artists list
                    album.setSongs(new ArrayList<>());   // Initialize songs list
                    albumRepository.save(album); // Save album to ensure it's persisted
                }

                // Step 3: Create the new song
                Song chanson = new Song();
                chanson.setTitre(titre);
                chanson.setImage(imageUrl);
                chanson.setDuree(duree);
                chanson.setChemin_audio(uploadDir + fileName);
                chanson.setAlbum(album); // Associate the album

                // Step 4: Handle artists
                List<String> artistNames = Arrays.stream(nameArtists.split(","))
                        .map(String::trim)
                        .filter(name -> !name.isEmpty())
                        .collect(Collectors.toList());

                List<Artist> artists = new ArrayList<>();
                for (String artistName : artistNames) {
                    Artist artist = artistRepository.findByName(artistName);
                    if (artist == null) {
                        artist = new Artist();
                        artist.setName(artistName);
                        artist.setImage(imageUrl); // Use the same image for simplicity
                        artist.setAlbums(new ArrayList<>());
                        artist.setSongs(new ArrayList<>());
                    }
                    // Add song to artist and save
                    artist.getSongs().add(chanson);
                    artistRepository.save(artist);
                    artists.add(artist);
                }

                // Step 5: Finalize associations
                album.getSongs().add(chanson); // Add song to album
                album.getArtists().addAll(artists); // Add artists to album
                chanson.setArtists(artists); // Associate artists with the song

                // Save everything
                songRepository.save(chanson);

                // Step 6: Upload the file
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File destinationFile = new File(uploadDir + fileName);
                try (FileOutputStream fos = new FileOutputStream(destinationFile)) {
                    fos.write(file.getBytes());
                }

                System.out.println("File uploaded: " + uploadDir + fileName);

            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Error uploading the song");
                e.printStackTrace();
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error adding the song");
                e.printStackTrace();
            }

            return "redirect:/admin";
        }

    @DeleteMapping("/supprimerChanson")
    public String supprimerChanson(Model model, @RequestParam("name") String name) {
    	Song song = songRepository.findByTitre(name);
        if (song != null) {
        	songRepository.delete(song);
        	//supprime la relation artiste song (à voir pouvoir album song et playlist song)
        	//peut être faire une recherche par artiste en +
        }
        else {
        	model.addAttribute("error", "la musique saisie n'existe pas");
        }
        return "redirect:/admin";
    }
    
    @DeleteMapping("/supprimerUser")
    public String supprimerUser(Model model, @RequestParam("username") String username) {
    	User user = userRepository.findByUsername(username);
        if (user != null) {
        	userRepository.delete(user);
        	//supprime la relation artiste song (à voir pouvoir album song et playlist song)
        	//peut être faire une recherche par artiste en +
        }
        else {
        	model.addAttribute("error", "l'utilisateur saisit n'existe pas");
        }
        return "redirect:/admin";
    }







}
