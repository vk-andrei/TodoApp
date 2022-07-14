package com.example.todoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class NoteDetailsFragment extends Fragment {

    static final String SELECTED_NOTE = "index";
    private Note note;

    public NoteDetailsFragment() {
        // required empty public constructor.    wtf????
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // WTF????
       /* if (savedInstanceState != null) {
            requireActivity().getSupportFragmentManager().popBackStack();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();

        Button btnBack = view.findViewById(R.id.fragment_note_detail_btn);
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack(); // УБИРАЕТ ФРАГМЕНТ ЖЕ?
        });


        if (arguments != null) {

            Note paramNote = arguments.getParcelable(SELECTED_NOTE);
            // найдем ИМЕННО ТУ ЗАМЕТКУ по НАЗВАНИЮ путем перебора всех заметок:
            note = Arrays.stream(Note.getNotes()).filter(n -> n.getId() == paramNote.getId()).findFirst().get();

            TextView tV_title = view.findViewById(R.id.fragment_note_title);
            //tV_title.setText(Note.getNotes()[index].getTitle());
            tV_title.setText(note.getTitle());
            // CHANGING OUR TITLE???
            tV_title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //Note.getNotes()[index].setTitle(charSequence.toString());
                    note.setTitle(tV_title.getText().toString());
                    updateData();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            TextView tV_description = view.findViewById(R.id.fragment_note_description);
            //tV_description.setText(Note.getNotes()[index].getDescription());
            tV_description.setText(note.getDescription());

            TextView tV_data = view.findViewById(R.id.fragment_note_data);
            //tV_data.setText(Note.getNotes()[index].getDateTime());
            tV_data.setText(note.getDateTime().toString()); // ?????
        }
    }

    private void updateData() {
        ListOfTitlesFragment listOfTitlesFragment = (ListOfTitlesFragment) requireActivity().getSupportFragmentManager()
                .getFragments().stream()
                .filter(fragment -> fragment instanceof ListOfTitlesFragment)
                .findFirst()
                .get();

        listOfTitlesFragment.initNotes();
    }


    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы
    public static NoteDetailsFragment newInstance(int index) {
        // Создание фрагмента
        NoteDetailsFragment fragment = new NoteDetailsFragment();

        // Передача параметра через бандл
        Bundle args = new Bundle();
        args.putInt(SELECTED_NOTE, index);
        fragment.setArguments(args);
        return fragment;
    }

    // ПЕРЕГРУЖЕННЫЙ МЕТОД для создания ОБЪЕКТОВ по самому ОБЪЕКТУ
    public static NoteDetailsFragment newInstance(Note note) {
        // Создание фрагмента
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        // Передача параметра через бандл
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

}