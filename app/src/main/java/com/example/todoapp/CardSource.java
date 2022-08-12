package com.example.todoapp;

public interface CardSource {

    NoteCard getNoteCard(int position);

    int size();                                 // qty of notes
}
