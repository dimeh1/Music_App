package com.example.music_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.music_app.model.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>{
	Artist findByName (String name);
}
