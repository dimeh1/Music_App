package com.example.music_app.repository;

import com.example.music_app.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Integer> {
    // Méthode pour récupérer les paramètres d'un utilisateur spécifique (si nécessaire)
    Optional<Settings> findById(Integer id);
}
