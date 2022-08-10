package com.example.todoapp;

import static com.example.todoapp.NoteDetailsFragment.SELECTED_NOTE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.Optional;

public class ListOfTitlesFragment extends Fragment {

    Note note;
    View dataContainer;

    public ListOfTitlesFragment() {
    }

    @Override // РАЗОБРАТЬСЯ!!!!!!!!!!!!!!
    public void onSaveInstanceState(@NonNull Bundle outState) {

        if (note == null) {
            if (Note.getNotes().size() > 0) {
                note = Note.getNotes().get(0);
            }
        }

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
            Note paramNote = savedInstanceState.getParcelable(SELECTED_NOTE);
            Optional<Note> selectedNote = Note.getNotes().stream().filter(n -> n.getId() == paramNote.getId()).findFirst();

            note = selectedNote.orElseGet(() -> Note.getNotes().get(0));
            //note = savedInstanceState.getParcelable(SELECTED_NOTE);
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
        // В этом цикле создаём элемент TextView,
        // заполняем его значениями и добавляем на экран.
        // Note.getNotes() - array of our NOTES

        // При помощи этого объекта будем доставать элементы, спрятанные в item.xml
        LayoutInflater inflater = getLayoutInflater();

        for (int i = 0; i < Note.getNotes().size(); i++) {
            /*TextView tVnoteTitle = new TextView(getContext());
            tVnoteTitle.setText(Note.getNotes().get(i).getTitle());
            tVnoteTitle.setTextSize(20);
            linearLayout.addView(tVnoteTitle);*/

            // Get element from one_line_note.xml
            View item = inflater.inflate(R.layout.one_line_note, linearLayout, false);
            // Find here needed elements
            TextView tVnoteTitle = item.findViewById(R.id.tv_one_line_note_title);
            tVnoteTitle.setText(Note.getNotes().get(i).getTitle());
            tVnoteTitle.setTextSize(20);
            linearLayout.addView(item);

            final int index = i;

            initPopupmenu(linearLayout, tVnoteTitle, index);

            tVnoteTitle.setOnClickListener(v -> {
                //showNoteDetailsFragment(Note.getNotes()[index]);
                showNoteDetailsFragment(Note.getNotes().get(index));
            });
        }
    }

    // Тут Меню POPUP нужно нам чтобы УДАЛИТЬ элемент:
    // а именно: удалить из коллекции И удалить текстовое поле с ним немедленно по кот. кликаем
    // для этого сделаем метод, в кот передадим:
    // 1. рутовое View. Контейнер где это все нах-ся
    // 2. каком элементе мы его вызываем
    // 3. index заметки
    @SuppressLint("NonConstantResourceId")
    private void initPopupmenu(View rootView, TextView tv, int index) {
        tv.setOnLongClickListener(v -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, tv);
            activity.getMenuInflater().inflate(R.menu.note_popup, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.action_popup_delete:
                        Note deletedNote = Note.getNotes().remove(index);
                        ((LinearLayout) rootView).removeView(tv);
                        note = Note.getNote(index);
                        Snackbar.make(rootView, "Note " + note.getTitle() + " was deleted", Snackbar.LENGTH_SHORT)
                                .setAction(R.string.cancel_text, v1 -> {
                                    Note.getNotes().add(deletedNote);
                                    initNotes();
                                })
                                .show();
                        initNotes(); // без этой строчки слетали индексы! в портретном режиме
                        return true;
                }
                return true;
            });
            popupMenu.show();
            return true;
        });
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
        ft.add(R.id.main_container, mNoteDetailsFragment);
        ft.addToBackStack("");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    private void showNoteDetailsFragmentLandscape(Note note) {
        NoteDetailsFragment mNoteDetailsFragment = NoteDetailsFragment.newInstance(note);
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_container_details, mNoteDetailsFragment); // замена ФРАГМЕНТА
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

}