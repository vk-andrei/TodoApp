package com.example.todoapp.data;

import android.content.res.Resources;
import android.util.Log;

import com.example.todoapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CardSourceImpl implements CardSource {

    private List<NoteCard> noteCardList;
    private Resources resources; // для доступа к ресурсам (массивы и проч)

    public CardSourceImpl(Resources resources) {
        noteCardList = new ArrayList<>(10);
        this.resources = resources;
    }

    public CardSourceImpl init() {
        int[] noteIds = resources.getIntArray(R.array.id);
        Log.d("TAG", "CARDSOURCE init: ids:" + Arrays.toString(noteIds));
        String[] noteTitles = resources.getStringArray(R.array.titles);
        String[] noteDescriptions = resources.getStringArray(R.array.descriptions);
        for (int i = 0; i < noteIds.length; i++) {
            noteCardList.add(new NoteCard(noteIds[i], noteTitles[i], noteDescriptions[i], Calendar.getInstance().getTime()));
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

    @Override
    public void addNoteCard(NoteCard noteCard) {
        noteCardList.add(noteCard);
    }

    @Override
    public void delNoteCard(int position) {
        noteCardList.remove(position);
    }

    @Override
    public void updateNoteCard(int position, NoteCard noteCard) {
        noteCardList.set(position, noteCard);
    }

    @Override
    public void clearNoteCards() {
        noteCardList.clear();
    }
}
