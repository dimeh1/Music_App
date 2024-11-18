package com.example.music_app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.music_app.model.Playlist;
import com.example.music_app.model.Song;
import com.example.music_app.model.User;
import com.example.music_app.repository.PlaylistRepository;
import com.example.music_app.repository.SongRepository;
import com.example.music_app.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PlaylistController {
	
	@Autowired
	PlaylistRepository playlistRepository;
	@Autowired
	SongRepository songRepository;
	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("/playlist/{id}")
	public String viewPlaylist(@PathVariable("id") Long playlistId, Model model, HttpSession session) {
	    if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
	        User user = (User) session.getAttribute("user");

	        Optional<Playlist> playlistOptional = playlistRepository.findByIdAndUser(playlistId, user);
	        if (playlistOptional.isPresent()) {
	            Playlist playlist = playlistOptional.get();
	            User userSession = userRepository.findByEmail(user.getEmail()).get();
	            
	            model.addAttribute("user", userSession);
	            model.addAttribute("playlists", userSession.getPlaylists());
	            model.addAttribute("playlist", playlist);
	            model.addAttribute("songs", playlist.getSongs());
	            return "playlist";
	        } else {
	            model.addAttribute("error", "Playlist introuvable ou non autorisée.");
	        }
	    } else {
	        model.addAttribute("error", "Vous devez être connecté pour voir une playlist.");
	        return "redirect:/login";
	    }
	    return "redirect:/";
	}

	
	//Création d'une playlist pour l'utilisateur
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
	
	//Mise à jour de la playlist en modifiant son nom
	@PostMapping("/update-playlist")
	public String updatePlaylist(Model model, @RequestParam("playlistId") Long playlistId, @RequestParam("name") String name, HttpSession session) {
		if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
			User user = (User) session.getAttribute("user");
			
			Optional<Playlist> playlistOptional = playlistRepository.findByIdAndUser(playlistId, user);
			if (playlistOptional.isPresent()) {
				Playlist playlist = playlistOptional.get();
					playlist.setName(name);
					
					playlistRepository.save(playlist);
					return "redirect:/playlist/" + playlistId;
			}
			else {
				model.addAttribute("error", "La playlistest introuvable.");
			}
		}
		return "redirect:/";
	}
	
	//Ajout d'une musique dans la playlist Like
	@PostMapping("/add-like")
	public String addSongInLike(Model model, @RequestParam("songId") Long songId, HttpSession session) {
	    if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {
	        
	        User user = (User) session.getAttribute("user");

	        Optional<Song> songOptional = songRepository.findById(songId);
	        
	        if (songOptional.isPresent()) {
	            Song song = songOptional.get();
	            Optional<Playlist> likePlaylistOptional = playlistRepository.findByIdAndUser(1L, user);
	            
	            if (likePlaylistOptional.isPresent()) {
	            	Playlist likePlaylist = likePlaylistOptional.get();
	            	
		            if (!likePlaylist.getSongs().contains(song)) {
		            	likePlaylist.getSongs().add(song);
		                playlistRepository.save(likePlaylist);
		                
		                return "redirect:/playlist/" + likePlaylist.getId();
		            } else {
		            	model.addAttribute("error", "Cette musique est déjà dans votre playlist Like.");
		            }
	            }
	            else {
	            	model.addAttribute("error", "La playlist 'Like' est introuvable");
	            }
	        } else {
	            model.addAttribute("error", "Musique non trouvée.");
	        }
	    } else {
	        model.addAttribute("error", "Vous devez être connecté pour ajouter une musique à votre playlist.");
	    }
	    return "redirect:/";
	}

	//Suppression d'une musique dans une playlist
	@PostMapping("remove-song")
	public String removeSongFromPlaylist(Model model, @RequestParam("songId") Long songid, @RequestParam("playlistId") Long playlistId, HttpSession session) {
		if (session.getAttribute("loggedin") != null && (boolean) session.getAttribute("loggedIn")) {
			User user = (User) session.getAttribute("user");
			
			Optional<Song> songOptional = songRepository.findById(songid);
			if (songOptional.isPresent()) {
				Song song = songOptional.get();
				Optional<Playlist> playlistOptional = playlistRepository.findByIdAndUser(playlistId, user);
				
				if (playlistOptional.isPresent()) {
					Playlist playlist = playlistOptional.get();
					
					if (playlist.getSongs().contains(song)) {
							
						playlist.getSongs().remove(song);
						song.getPlaylists().remove(playlist);
							
						playlistRepository.save(playlist);
						songRepository.save(song);
							
						return "redirect:/playlist/" + playlistId;
					}
					else {
						model.addAttribute("error", "La musique n'est pas présente dans cette playlist");
					}
				}
				else {
					model.addAttribute("error", "La playlist est introuvable.");
				}
			}
			else {
				model.addAttribute("error", "Musique non trouvée.");
			}
		}
		else {
			model.addAttribute("error", "Vous devez être connecté pour ajouter une musique à votre playlist.");
		}
		return "redirect:/";
	}
	
	//Suppression de la playlist
	@PostMapping("/delete-playlist")
	public String deletePlaylist (Model model, @RequestParam("playlistId") Long playlistId, HttpSession session) {
		if (session.getAttribute("user") != null && (boolean) session.getAttribute("loggedIn")) {
			User user = (User) session.getAttribute("user");

			Optional<Playlist> playlistOptional = playlistRepository.findByIdAndUser(playlistId, user);
			if (playlistOptional.isPresent()) {
				Playlist playlist = playlistOptional.get();
				for (Song song : playlist.getSongs()) {
					song.getPlaylists().remove(playlist);
				}

				playlist.getSongs().clear();
				playlistRepository.delete(playlist);			}
			else {
				model.addAttribute("error", "La playlist est introuvable.");
			}
		}
		else {
			model.addAttribute("error", "Vous devez être connecté pour supprimer une playlist.");
		}

		return "redirect:/";
	}


	@PostMapping("/add-song-to-playlist")
	public String addSongToPlaylist(@RequestParam("playlistId") Long playlistId,
									@RequestParam("songId") Long songId,
									Model model, HttpSession session) {
		if (session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn")) {

			User user = (User) session.getAttribute("user");

			Optional<Song> songOptional = songRepository.findById(songId);
			Optional<Playlist> playlistOptional = playlistRepository.findByIdAndUser(playlistId, user);

			if (playlistOptional.isPresent() && songOptional.isPresent()) {
				Playlist playlist = playlistOptional.get();
				Song song = songOptional.get();

				if (playlist.getSongs().contains(song)) {
					playlist.getSongs().add(song);
					playlistRepository.save(playlist);

					return "redirect:/playlist/" + playlist.getId();
				} else {
					model.addAttribute("error", "Cette musique est déjà dans votre playlist Like.");
				}

			} else {
				model.addAttribute("error", "Musique non trouvée.");
			}
		} else {
			model.addAttribute("error", "Vous devez être connecté pour ajouter une musique à votre playlist.");
		}
		return "redirect:/";
	}

}
