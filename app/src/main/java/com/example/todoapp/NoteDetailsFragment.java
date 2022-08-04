package com.example.todoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

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
        // В момент создания нов фрагмента мы проверяем создается ли фрагмент впервые, и если да,
        // то удаляем его из бэкстека
        if (savedInstanceState != null) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Создает ТОЛЬКО ОДИН РАЗ (а то будет постоянно создавать эти меню!)
        if (savedInstanceState == null) {
            // ЕСЛИ мы хотим увидеть ОПРЕДЕЛЕННОЕ МЕНЮ в рамках некоторого ФРАГМЕНТА: (1)
            setHasOptionsMenu(true);// доступ к меню (ТУЛБАРУ) АКТИВИТИ
        }
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    // ЕСЛИ мы хотим увидеть ОПРЕДЕЛЕННОЕ МЕНЮ в рамках некоторого ФРАГМЕНТА: (2)
    // ПЕРЕОПРЕДЕЛЯЕМ OPTIONS MENU
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Например, хотим, чтобы меню EXIT не было доступно в нашем фрагменте
 /*       MenuItem menuItemExit = menu.findItem(R.id.menu_action_exit);
        if (menuItemExit != null) {
            menuItemExit.setVisible(false);
        }*/
        // И меню ABOUT тоже уберем:
        MenuItem menuItemAbout = menu.findItem(R.id.menu_action_about);
        if (menuItemAbout != null) {
            menuItemAbout.setVisible(false);
        }
        // И меню FIND тоже уберем:
        MenuItem menuItemFind = menu.findItem(R.id.menu_action_find);
        if (menuItemFind != null) {
            menuItemFind.setVisible(false);
        }

        // А это СОБСТВЕННОЕ меню ФРАГМЕНТА
        inflater.inflate(R.menu.note_menu, menu);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_action_delete) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Alert!");
            builder.setMessage("Do you really want to this delete note?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO при удалении убирать из ФРАГМЕНТА ДЕТАЛИЗАЦИИ удаленную заметку
                    Note deletedNote = note;
                    Note.getNotes().remove(note);
                    View view = getView();
                    assert view != null;
                    Snackbar.make(view, "Note " + note.getTitle() + " was deleted", Snackbar.LENGTH_SHORT)
                            /*.setAction(R.string.cancel_text, v -> {
                                Note.getNotes().add(deletedNote);
                                //updateData();*/
                            .show();
                    updateData();

                    if (!isLandscape()) {
                        requireActivity().getSupportFragmentManager().popBackStack(); // ТОЛЬКО В ПОРТРЕТНОМ РЕЖИМЕ
                    } else { // Указываем фокус на ЗАМЕТКУ под номером 0
                        if (Note.getNotes().size() > 0) {
                            note = Note.getNotes().get(0);
                        }
                    }
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();

            return true; // без этого тоже работает. Что логично.
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();

        Button btnBack = view.findViewById(R.id.fragment_note_detail_btn);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().popBackStack(); // УБИРАЕТ ФРАГМЕНТ ЖЕ?
            });
        }

        if (arguments != null) {
            Note paramNote = arguments.getParcelable(SELECTED_NOTE);
            // найдем ИМЕННО ТУ ЗАМЕТКУ по НАЗВАНИЮ путем перебора всех заметок:
            // note = Arrays.stream(Note.getNotes()).filter(n -> n.getId() == paramNote.getId()).findFirst().get();
            // note = Note.getNotes().stream().filter(note1 -> note1.getId() == paramNote.getId()).findFirst().get();

            if (paramNote != null) {
                // Проверим существует ли еще заметка?
                Optional<Note> selectedNote = Note.getNotes().stream().filter(note1 -> note1.getId() == paramNote.getId()).findFirst();
                /*if (selectedNote.isPresent()) {
                    note = selectedNote.get();
                } else {
                    note = Note.getNotes().get(0);
                }*/
                // ТОЖЕ САМОЕ:
                note = selectedNote.orElseGet(() -> Note.getNotes().get(0));
            } else { // САМ ДОБАВИЛ. ПРАВИЛЬНО ЖЕ????
                note = Note.getNotes().get(0);
            }

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