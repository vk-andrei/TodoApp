package com.example.todoapp;

import java.time.LocalDateTime;

public interface iNote {

    Note setTitle(String title);

    Note setDescription(String description);

    Note setDate(LocalDateTime dateTime);
}
