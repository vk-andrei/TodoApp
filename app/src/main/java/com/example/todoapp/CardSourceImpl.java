package com.example.todoapp;

import android.content.res.Resources;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CardSourceImpl implements CardSource {

    private List<NoteCard> noteCardList;
    private Resources resources; // для доступа к ресурсам (массивы и проч)

    public CardSourceImpl(Resources resources) {
        noteCardList = new ArrayList<>(20);
        this.resources = resources;
    }

    public CardSourceImpl init() {
        int[] noteIds = resources.getIntArray(R.array.id);
        String[] noteTitles = resources.getStringArray(R.array.titles);
        String[] noteDescriptions = resources.getStringArray(R.array.descriptions);
        for (int i = 0; i < noteIds.length; i++) {
            noteCardList.add(new NoteCard(noteIds[i], noteTitles[i], noteDescriptions[i], LocalDateTime.now()));
        }
        return this;
    }

    @Override
    public NoteCard getNoteCard(int position) {
        return noteCardList.get(position);
    }

    @Override
    public int size() {
        return noteCardList.size();
    }
}
