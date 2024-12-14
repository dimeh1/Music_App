package com.example.music_app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.music_app.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.music_app.model.Song;
import com.example.music_app.model.User;
import com.example.music_app.repository.PlaylistRepository;
import com.example.music_app.repository.SongRepository;
import com.example.music_app.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/songs")
public class SongAPI {

        @Autowired
        private SongRepository songRepository;

        @GetMapping("/{id}")
        public ResponseEntity<?> getSongById(@PathVariable Long id) {
            Optional<Song> songOptional = songRepository.findById(id);
            if (songOptional.isPresent()) {
                Song song = songOptional.get();
                Map<String, Object> songData = new HashMap<>();
                songData.put("title", song.getTitre());
                songData.put("artists", song.getArtists().stream().map(Artist::getName).collect(Collectors.toList()));
                songData.put("album", song.getAlbum().getNom());
                songData.put("path", song.getChemin_audio());
                return ResponseEntity.ok(songData);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found");
        }


}
