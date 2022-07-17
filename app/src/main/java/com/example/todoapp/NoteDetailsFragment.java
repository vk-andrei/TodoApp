package com.example.todoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class NoteDetailsFragment extends Fragment {

    static final String SELECTED_NOTE = "note";
    private Note note;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public NoteDetailsFragment() {
        // required empty public constructor.    wtf????
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ЕСЛИ мы хотим увидеть ОПРЕДЕЛЕННОЕ МЕНЮ в рамках некоторого ФРАГМЕНТА: (1)
        setHasOptionsMenu(true);

        /*if (savedInstanceState != null) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }*/
    }

    // ЕСЛИ мы хотим увидеть ОПРЕДЕЛЕННОЕ МЕНЮ в рамках некоторого ФРАГМЕНТА: (2)
    // ПЕРЕОПРЕДЕЛЯЕМ OPTIONS MENU
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Например, хотим, чтобы меню EXIT не было доступно в нашем фрагменте
        MenuItem menuItemExit = menu.findItem(R.id.menu_action_exit);
        if (menuItemExit != null) {
            menuItemExit.setVisible(false);
        }
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
            tV_title.setText(note.getTitle());
            // CHANGING OUR TITLE???
            tV_title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    note.setTitle(tV_title.getText().toString());
                    updateData();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            TextView tV_description = view.findViewById(R.id.fragment_note_description);
            tV_description.setText(note.getDescription());
            tV_description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    note.setDescription(tV_description.getText().toString());
                    updateData();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            TextView tV_data = view.findViewById(R.id.fragment_note_data);
            LocalDateTime date = note.getDateTime();
            String dateFormatted = date.format(formatter);
            tV_data.setText(dateFormatted);
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