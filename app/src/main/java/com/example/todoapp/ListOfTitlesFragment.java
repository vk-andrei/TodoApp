package com.example.todoapp;

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
import android.widget.Toast;

import static com.example.todoapp.NoteDetailsFragment.SELECTED_NOTE;

public class ListOfTitlesFragment extends Fragment {

    Note note;
    View dataContainer;

    public ListOfTitlesFragment() {
    }

    @Override // РАЗОБРАТЬСЯ!!!!!!!!!!!!!!
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(SELECTED_NOTE, note);
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
        return inflater.inflate(R.layout.fragment_list_of_titles, container, false);
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override // CALLBACK - вызывается когда ФРАГМЕНТ уже СОЗДАН
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            note = savedInstanceState.getParcelable(SELECTED_NOTE);
        }

        dataContainer = view.findViewById(R.id.fragment_list_of_notes_container);
        initNotes(dataContainer);

        if (isLandscape()) {
            //showNoteDetailsFragmentLandscape(selectedIndex);
            showNoteDetailsFragmentLandscape(note);
        }
    }

    // ПЕРЕГРУЖЕННЫЙ МЕТОД (????????????? для чего?)
    public void initNotes() {
        initNotes(dataContainer);
    }

    private void initNotes(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        linearLayout.removeAllViews(); // ????????????????????????????
        // создаём список заметок на экране из массива в ресурсах
        // В этом цикле создаём элемент TextView,
        // заполняем его значениями и добавляем на экран.
        // Note.getNotes() - array of our NOTES
        for (int i = 0; i < Note.getNotes().length; i++) {
            TextView tVnoteTitle = new TextView(getContext());
            tVnoteTitle.setText(Note.getNotes()[i].getTitle());
            tVnoteTitle.setTextSize(20);
            linearLayout.addView(tVnoteTitle);

            final int index = i;
            tVnoteTitle.setOnClickListener(v -> {
                showNoteDetailsFragment(Note.getNotes()[index]);
            });
        }
    }

    private void showNoteDetailsFragment(Note note) {
        this.note = note;
        if (isLandscape()) {
            showNoteDetailsFragmentLandscape(note);
        } else {
            showNoteDetailsFragmentPortrait(note);
        }
    }

    private void showNoteDetailsFragmentPortrait(Note note) {
        NoteDetailsFragment mNoteDetailsFragment = NoteDetailsFragment.newInstance(note);
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // put fragment in MAIN ACTIVITY:
        ft.add(R.id.main_container, mNoteDetailsFragment);
        ft.addToBackStack("");
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