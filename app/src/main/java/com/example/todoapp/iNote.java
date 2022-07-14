package com.example.todoapp;

import java.time.LocalDateTime;

public interface iNote {

    void setTitle(String title);

    void setDescription(String description);

    void setDate(LocalDateTime dateTime);
}
