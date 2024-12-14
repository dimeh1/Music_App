package com.example.music_app.controller;

import com.example.music_app.model.Settings;
import com.example.music_app.model.Theme;
import com.example.music_app.model.User;
import com.example.music_app.repository.SettingsRepository;
import com.example.music_app.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class SettingsController {

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Affiche la page des paramètres de l'utilisateur
     * @param model le modèle pour la vue
     * @param session la session utilisateur
     * @return le nom de la page HTML des paramètres
     */
    @GetMapping("/settings")
    public String settingsPage(Model model, HttpSession session) {
        // Récupération de l'utilisateur
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn == null || !loggedIn) {
            return "redirect:/login";
        }

        // Récupération de l'utilisateur depuis la session
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        // Ajouter l'utilisateur au modèle
        model.addAttribute("user", user);

        // Ajouter les paramètres au modèle
        Settings settings = user.getSettings();
        model.addAttribute("settings", settings);

        return "settings"; // Affiche la vue settings.html
    }


    @PostMapping("/settings")
    public String changeTheme(@RequestParam("theme") String theme, HttpSession session) {
        // Vérification si l'utilisateur est connecté
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn == null || !loggedIn) {
            return "redirect:/login";
        }

        // Récupérer l'utilisateur depuis la session
        User user = (User) session.getAttribute("user");

        // Vérification supplémentaire si l'utilisateur est null
        if (user == null) {
            return "redirect:/login";
        }

        // Charger l'utilisateur depuis la base de données
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            return "redirect:/login"; // Utilisateur non trouvé
        }

        // Récupérer les paramètres associés à l'utilisateur
        Settings settings = user.getSettings();

        if (settings != null) {
            try {
                // Mettre à jour le thème (DARK ou LIGHT) et sauvegarder les paramètres
                settings.setTheme(Theme.valueOf(theme.toUpperCase()));
                settingsRepository.save(settings); // Sauvegarder les paramètres
            } catch (IllegalArgumentException e) {
                System.err.println("Thème non valide : " + theme);
            }
        } else {
            System.err.println("Aucun paramètre associé à l'utilisateur.");
        }

        // Mettre à jour la session avec l'utilisateur mis à jour
        session.setAttribute("user", user);

        return "redirect:/settings"; // Redirige vers la page des paramètres
    }
}
