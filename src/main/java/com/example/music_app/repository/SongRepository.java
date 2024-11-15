package com.example.music_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.music_app.model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>{
<<<<<<< HEAD
	Song findByTitre (String titre);
=======
	Song findByTitre(String titre);
>>>>>>> 6770e2362b2c569d36f931650fb653799d3f232d
	Optional<Song> findById(Long id);
}
