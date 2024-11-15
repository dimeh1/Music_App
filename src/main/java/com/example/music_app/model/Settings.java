package com.example.music_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="Settings")
public class Settings {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Theme theme = Theme.DARK;

    public Settings() {
    }

    public Settings(Theme theme){
        this.theme = theme;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
