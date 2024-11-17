package com.example.music_app.controller;

import com.example.music_app.model.Settings;
import com.example.music_app.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.music_app.model.Playlist;
import com.example.music_app.model.Status;
import com.example.music_app.model.User;
import com.example.music_app.repository.PlaylistRepository;
import com.example.music_app.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private SettingsRepository settingsRepository;

	public String hashPassword(String password) {
		return passwordEncoder.encode(password);
	}

	public boolean verification(String password, String passwordEncoded) {
		return passwordEncoder.matches(password, passwordEncoded);
	}

	@Autowired
	UserRepository userRepository;

	@Autowired
	PlaylistRepository playlistRepository;

	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String login (Model model, @RequestParam("email") String email, @RequestParam("password") String password,
						 HttpSession session) {
		if (email != null && password != null) {
			if (userRepository.findByEmail(email).isPresent() && this.verification(password, userRepository.findByEmail(email).get().getPassword())) {

				session.setAttribute("loggedIn", true);
				session.setAttribute("user", userRepository.findByEmail(email).get());
				session.setAttribute("email", userRepository.findByEmail(email).get());

				return "redirect:/";
			} else {
				model.addAttribute("erreur", "Adresse email ou mot de passe incorrect.");
				return "login";
			}

		}
		model.addAttribute("erreur", "Veuillez saisir une adresse email et un mot de passe.");
		return "login";
	}


	@GetMapping("/signup")
	public String showSignUp() {
		return "signup";
	}

	@PostMapping("/signup")
	public String signup(Model model, @RequestParam("username") String username, @RequestParam("email") String email,
						 @RequestParam("password") String password, HttpSession session){

		if (username != null && email != null && password != null) {
			if (userRepository.findByEmail(email).isEmpty()) {
				User user = new User();
				user.setUsername(username);
				user.setEmail(email);
				user.setPassword(this.hashPassword(password));
				user.setStatus(Status.GUEST);

				Settings settings = new Settings();
				user.setSettings(settings);


				Playlist like = new Playlist();
				like.setName("Like");
				like.setUser(user);
				user.getPlaylists().add(like);

				settingsRepository.save(settings);
				userRepository.save(user);
				playlistRepository.save(like);

				return "redirect:/login";
			} else {
				model.addAttribute("erreur", "L'adresse email est déja utilisé.");
			}
		}
		model.addAttribute("erreur", "Veuillez remplir tout les champs.");
		return "signup";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}