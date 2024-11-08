package com.example.music_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.music_app.model.User;
import com.example.music_app.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/")
	public String home(Model model, HttpSession session) {
	    Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
	    if (loggedIn != null && loggedIn) {
	    	User userSession = (User) session.getAttribute("user");
	    	User user = userRepository.findByEmail(userSession.getEmail()).get();
	    	
	        model.addAttribute("user", user);
	        model.addAttribute("playlists", user.getPlaylists());
	    } else {
	    	model.addAttribute("user",null);
	    }
	    return "home";
	}

}
