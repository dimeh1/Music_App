package com.example.music_app.repository;

import com.example.music_app.model.Playlist;
import com.example.music_app.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
}
