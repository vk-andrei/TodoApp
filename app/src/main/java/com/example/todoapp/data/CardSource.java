package com.example.todoapp.data;

import java.util.List;

public interface CardSource {

    NoteCard getNoteCard(int position);

    int size();                                 // qty of notes

    void addNoteCard(NoteCard noteCard);

    void delNoteCard(int position);

    void updateNoteCard(int position, NoteCard noteCard);

    void clearNoteCards();

    List<NoteCard> getNoteCardList();

    void setNoteCardList (List<NoteCard> noteCardList);

}
