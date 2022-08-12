package com.example.todoapp;

import java.time.LocalDateTime;

public class NoteCard {
    private int id;
    private String title;
    private String description;
    private LocalDateTime dateTime;


    public NoteCard(int id, String title, String description, LocalDateTime dateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return String.format("Note - id: %s, title: %s, description: %s, date: %s", id, title, description, dateTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}