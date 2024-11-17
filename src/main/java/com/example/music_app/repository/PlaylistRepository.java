package com.example.music_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.music_app.model.Playlist;
import com.example.music_app.model.User;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
	Optional<Playlist> findByIdAndUser(Long id, User user);
}
