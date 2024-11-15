package com.example.music_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.music_app.model.Playlist;
import com.example.music_app.model.User;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
	Playlist findByIdAndUser(Long id, User user);
}
