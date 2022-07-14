package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.todoapp.NoteDetailsFragment.SELECTED_NOTE;

public class NoteFragment extends Fragment {

    // int selectedIndex = 0;
    // Переделаем, чтобы передавались ОБЪЕКТЫ
    Note note;

    @Override // РАЗОБРАТЬСЯ!!!!!!!!!!!!!!
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(SELECTED_NOTE, note);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // CALLBACK - вызывается когда происходит попытка создания ФРАГМЕНТА
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override // CALLBACK - вызывается когда ФРАГМЕНТ уже СОЗДАН
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            //selectedIndex = savedInstanceState.getInt(SELECTED_NOTE, 0);
            // переделаем на ОБЪЕКТ:
            note = (Note) savedInstanceState.getSerializable(SELECTED_NOTE);
        }


        View dataContainer = view.findViewById(R.id.fragment_note_container);
        initNotes(dataContainer);

        if (isLandscape()) {
            //showNoteDetailsFragmentLandscape(selectedIndex);
            showNoteDetailsFragmentLandscape(note);        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void initNotes(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        // создаём список заметок на экране из массива в ресурсах
        //String[] notes = getResources().getStringArray(R.array.notes_array);
        // В этом цикле создаём элемент TextView,
        // заполняем его значениями и добавляем на экран.
        // Note.getNotes() - array of our NOTES
        for (int i = 0; i < Note.getNotes().length; i++) {
            TextView tVnoteTitle = new TextView(getContext());
            tVnoteTitle.setText(Note.getNote(i).getTitle());
            tVnoteTitle.setTextSize(20);
            linearLayout.addView(tVnoteTitle);

            int index = i;
            tVnoteTitle.setOnClickListener(view1 -> {
                showNoteDetailsFragment(index);
            });
        }
    }

    private void showNoteDetailsFragment(int index) {
        selectedIndex = index; // wtf?????
        if (isLandscape()) {
            showNoteDetailsFragmentLandscape(index);
        } else {
            showNoteDetailsFragmentPortrait(index);
        }
    }

    private void showNoteDetailsFragmentPortrait(int index) {

        /*NoteDetailsFragment mNoteDetailsFragment = NoteDetailsFragment.newInstance(index);
        // WTF requireActivity ????????????????????
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // put fragment in MAIN ACTIVITY:
        ft.add(R.id.main_container, mNoteDetailsFragment);
        // wtf ????????????
        ft.addToBackStack("");
        // wtf ????????????
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();*/

        // ПЕРЕДЕЛАЛИ НА АКТИВИТИ:

        Activity activity = requireActivity();
        Intent intent = new Intent(activity, NoteDetailsActivity.class);
        intent.putExtra(SELECTED_NOTE, index);
        startActivity(intent);
    }

    private void showNoteDetailsFragmentLandscape(int index) {
        NoteDetailsFragment mNoteDetailsFragment = NoteDetailsFragment.newInstance(index);
        // WTF requireActivity ????????????????????
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // put fragment in MAIN ACTIVITY:
        ft.replace(R.id.main_container_details, mNoteDetailsFragment); // замена ФРАГМЕНТА
        // wtf ????????????
        //ft.addToBackStack("");
        // wtf ????????????
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    // ПЕРЕГРУЖЕННЫЙ МЕТОД ДЛЯ ПРИНЯТИЯ ОБЪЕКТОВ:
    private void showNoteDetailsFragmentLandscape(Note note) {
        NoteDetailsFragment mNoteDetailsFragment = NoteDetailsFragment.newInstance(note);
        // WTF requireActivity ????????????????????
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // put fragment in MAIN ACTIVITY:
        ft.replace(R.id.main_container_details, mNoteDetailsFragment); // замена ФРАГМЕНТА
        // wtf ????????????
        // ft.addToBackStack("");
        // wtf ????????????
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}