package com.example.todoapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.adapters.RecyclerViewAdapter;
import com.example.todoapp.data.CardSource;
import com.example.todoapp.data.CardSourceImpl;
import com.example.todoapp.data.NoteCard;
import com.example.todoapp.observe.Observer;
import com.example.todoapp.observe.Publisher;

import java.util.Calendar;

public class ListOfTitlesFragment extends Fragment {

    private static final int DEFAULT_DURATION = 500;
    private CardSource noteCardSource;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    private Navigation navigation;
    private Publisher publisher;

    // признак, что при повторном открытии фрагмента (возврате из фрагмента, добавляющего запись)
    // надо прыгнуть на последнюю запись
    private boolean moveToLastPosition;


    public static ListOfTitlesFragment newInstance() {
        return new ListOfTitlesFragment();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
/*    if (note == null) {
            if (Note.getNotes().size() > 0) {
                note = Note.getNotes().get(0);
            }
        }
        outState.putParcelable(SELECTED_NOTE, note);*/
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Получим источник данных для списка (перенесли сюда из onCreateView)
        noteCardSource = new CardSourceImpl(getResources()).init();
    }

    // Called when a fragment is first attached to its context.
    // Фрагмент цепляется к АКТИВИТИ. Поскольку у нас одна активити, то мы получаем из неё паблишер,
    // при помощи которого будем посылать сигнал при завершении фрагмента
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override // CALLBACK - вызывается когда происходит попытка создания ФРАГМЕНТА
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("TAG", "ListOfTitlesFragment: onCreateView.");
        View v = inflater.inflate(R.layout.fragment_list_of_titles, container, false);

        initView(v);
        setHasOptionsMenu(true);

       /*if (savedInstanceState != null) {
            Note paramNote = savedInstanceState.getParcelable(SELECTED_NOTE);
            Optional<Note> selectedNote = Note.getNotes().stream().filter(n -> n.getId() == paramNote.getId()).findFirst();
            note = selectedNote.orElseGet(() -> Note.getNotes().get(0));
            //note = savedInstanceState.getParcelable(SELECTED_NOTE);}
        if (isLandscape()) {showNoteDetailsFragmentLandscape(note);}*/

        return v;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.rv_notes_list);
        // Поскольку onCreateView запускается каждый раз при возврате в фрагмент, данные надо
        // создавать один раз. Поэтому перенесли в onCreate
        //noteCardSource = new CardSourceImpl(getResources()).init();

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.d("TAG", "initRecyclerView: setLayoutManager: " + linearLayoutManager);

        mAdapter = new RecyclerViewAdapter(noteCardSource, this);
        recyclerView.setAdapter(mAdapter);
        Log.d("TAG", "initRecyclerView: setAdapter: " + mAdapter);

        // Установим анимацию
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(DEFAULT_DURATION);
        animator.setRemoveDuration(DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);

        if (moveToLastPosition) {
            recyclerView.smoothScrollToPosition(noteCardSource.size() - 1);
            moveToLastPosition = false;
        }

        mAdapter.setItemClickListener(new OnItemClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), String.format("Position - %d", position), Toast.LENGTH_SHORT).show();
            }
        });

        //initPopupmenu(linearLayout, tVnoteTitle, index);
    }


    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override // CALLBACK - вызывается когда ФРАГМЕНТ уже СОЗДАН
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

   /*
        if (savedInstanceState != null) {
            Note paramNote = savedInstanceState.getParcelable(SELECTED_NOTE);
            Optional<Note> selectedNote = Note.getNotes().stream().filter(n -> n.getId() == paramNote.getId()).findFirst();

            note = selectedNote.orElseGet(() -> Note.getNotes().get(0));
            //note = savedInstanceState.getParcelable(SELECTED_NOTE);
        }

        //dataContainer = view.findViewById(R.id.fragment_list_of_notes_container);
        dataContainer = view.findViewById(R.id.rv_notes_list);
        initNotes(dataContainer);

        if (isLandscape()) {
            //showNoteDetailsFragmentLandscape(selectedIndex);
            showNoteDetailsFragmentLandscape(note);
        }                                                                    */
    }



    /*public void initNotes() {
        //initNotes(dataContainer);
        initNotes(dataContainer);
    }

    private void initNotes(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        linearLayout.removeAllViews(); // ????????????????????????????
        // В этом цикле создаём элемент TextView,
        // заполняем его значениями и добавляем на экран.
        // Note.getNotes() - array of our NOTES

        // При помощи этого объекта будем доставать элементы, спрятанные в one_line_note.xml
        LayoutInflater inflater = getLayoutInflater();

        for (int i = 0; i < Note.getNotes().size(); i++) {
            *//*TextView tVnoteTitle = new TextView(getContext());
            tVnoteTitle.setText(Note.getNotes().get(i).getTitle());
            tVnoteTitle.setTextSize(20);
            linearLayout.addView(tVnoteTitle);*//*

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
    }*/


    // Тут Меню POPUP нужно нам чтобы УДАЛИТЬ элемент:
    // а именно: удалить из коллекции И удалить текстовое поле с ним немедленно по кот. кликаем
    // для этого сделаем метод, в кот передадим:
    // 1. рутовое View. Контейнер где это все нах-ся
    // 2. каком элементе мы его вызываем
    // 3. index заметки
/*    @SuppressLint("NonConstantResourceId")
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
    }*/

    /*private void showNoteDetailsFragment(Note note) {
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
    }*/

    // МЕНЮ СПРАВА (три точки). Убрал с MainActivity, вставил сюда. ХЗ ЗАЧЕМ
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idItemMenu = item.getItemId();
        switch (idItemMenu) {
            case R.id.menu_add_new_note:
/*                noteCardSource.addNoteCard(new NoteCard(noteCardSource.size(), "Note " + noteCardSource.size(),
                        "Description " + noteCardSource.size(), Calendar.getInstance().getTime()));
                mAdapter.notifyItemInserted(noteCardSource.size() - 1);
                recyclerView.scrollToPosition(noteCardSource.size() - 1);*/

                navigation.addFragment(NoteCardFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteCard(NoteCard noteCard) {
                        noteCardSource.addNoteCard(noteCard);
                        mAdapter.notifyItemInserted(noteCardSource.size() - 1);
                        // это сигнал, чтобы вызванный метод onCreateView перепрыгнул на конец списка
                        moveToLastPosition = true;
                        // После того как завершится редактирование элемента в новом фрагменте, мы вернёмся в метод
                        // обратного вызова наблюдателя Observer.updateCardData(), система начнёт обновлять этот
                        // фрагмент и вызовет метод onCreateView() повторно. Нам придётся пересоздать все элементы, а
                        // также адаптер. Поэтому вводится признак moveToLastPosition, означающий, что мы только что
                        // добавляли данные, чтобы перепрыгнуть на последний элемент. В методе initRecyclerView()
                        // вызывается переход на последний элемент.
                    }
                });
                return true;
            case R.id.menu_action_settings:
                ((MainActivity) requireActivity()).openSettingsFragment();
                return true;

            case R.id.menu_action_clear:
                noteCardSource.clearNoteCards();
                mAdapter.notifyDataSetChanged();
                return true;

            case R.id.menu_action_about:
                ((MainActivity) requireActivity()).openAboutFragment();
                return true;
            case R.id.menu_action_find:
                ((MainActivity) requireActivity()).openFindNote();
                return true;
            case R.id.menu_action_exit:
                ((MainActivity) requireActivity()).finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.note_context_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = mAdapter.getMenuPosition(); // смотри на каком элементе был КЛИК
        switch (item.getItemId()) {
            case R.id.action_context_menu_update:

                /*NoteCard editedNote = noteCardSource.getNoteCard(position);
                noteCardSource.updateNoteCard(position, new NoteCard(editedNote.getId(),
                        editedNote.getTitle() + " 1",
                        editedNote.getDescription() + 1, Calendar.getInstance().getTime()));
                mAdapter.notifyItemChanged(position);*/

                navigation.addFragment(NoteCardFragment.newInstance(noteCardSource.getNoteCard(position)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteCard(NoteCard noteCard) {
                        noteCardSource.updateNoteCard(position, noteCard);
                        mAdapter.notifyItemChanged(position);
                    }
                });
                return true;

            case R.id.action_context_menu_delete:
                noteCardSource.delNoteCard(position);
                mAdapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}