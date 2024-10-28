package com.example.music_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.music_app.model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long>{

}