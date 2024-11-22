package com.example.music_app.controller;

import com.example.music_app.model.Settings;
import com.example.music_app.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SettingsController {

    @Autowired
    SettingsRepository settingsRepository;

    @GetMapping("/settings")
    public String settingsPage(Model model) {
        model.addAttribute("currentTheme", settings.getTheme());
        return "settings"; // Le nom de la page HTML
    }

    @PostMapping("/settings")
    public String changeTheme(@RequestParam("theme") String theme) {
        settings.setTheme(Theme.valueOf(theme.toUpperCase()));
        return "redirect:/settings";
    }
}
