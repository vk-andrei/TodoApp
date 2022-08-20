package com.example.todoapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todoapp.R;
import com.example.todoapp.data.CardSourceImpl;
import com.example.todoapp.data.NoteCard;
import com.example.todoapp.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class
NoteCardFragment extends Fragment {

    private static final String ARG_NOTE_CARD = "Param_NoteCard";
    private NoteCard noteCard;   // Данные по карточке заметки
    private Publisher publisher; // Паблишер с пом, кот будем обмениваться данными
    CardSourceImpl noteCardList = CardSourceImpl.getInstanceDATA();

    private TextView note_id;
    private TextInputEditText note_title;
    private TextInputEditText note_description;
    private DatePicker note_datePicker;

    // Для редактирования данных
    public static NoteCardFragment newInstance(NoteCard noteCard) {
        NoteCardFragment noteCardFragment = new NoteCardFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE_CARD, noteCard);
        noteCardFragment.setArguments(args);
        return noteCardFragment;
    }

    // Для добавления новых данных
    public static NoteCardFragment newInstance() {
        NoteCardFragment noteCardFragment = new NoteCardFragment();
        return noteCardFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (savedInstanceState() != null) { КАКАЯ РАЗНИЦА???????????
        if (getArguments() != null) {
            //assert getArguments() != null; // ДЛЯ ЧЕГО ЭТО ??????????????????
            noteCard = getArguments().getParcelable(ARG_NOTE_CARD);
        }
    }

    // Called when a fragment is first attached to its context.
    // Фрагмент цепляется к АКТИВИТИ. Поскольку у нас одна активити, то мы получаем из неё паблишер,
    // при помощи которого будем посылать сигнал при завершении фрагмента
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();

    }

    // Called when the fragment is no longer attached to its activity. This is called after onDestroy()
    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_card, container, false);
        initView(view);
        // Если noteCard - пустая, то это ДОБАВЛЕНИЕ ЗАМЕТКИ (private NoteCard collectNoteCard())
        // а если нет, то ===> ЗАПОЛНЯЕМ VIEW!!!
        if (noteCard != null) {   /// TODO РАЗБЕРИСЬ ТУТ
            fillView();
        } else {
            // NEW NOTE! Then -> OnStop -> collectCard
            note_id.setText(String.valueOf(noteCardList.size()));
        }
        return view;
    }

    // Здесь соберем данные из VIEWs
    @Override
    public void onStop() {
        super.onStop();
        noteCard = collectNoteCard();
    }

    // передаем данные в паблишер
    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(noteCard);
    }

    private NoteCard collectNoteCard() {
        // TODO разобраться с АЙДИШНИКАМИ
        int note_id = Integer.parseInt(this.note_id.getText().toString());
        String note_title = this.note_title.getText().toString();
        String note_description = this.note_description.getText().toString();
        //Date date = Calendar.getInstance().getTime();
        Date date = getDateFromDatePicker();
        //return new NoteCard(note_id, note_title, note_description, date);
        return new NoteCard(note_id, note_title, note_description, date);
    }

    private Date getDateFromDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.note_datePicker.getYear());
        calendar.set(Calendar.MONTH, this.note_datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, this.note_datePicker.getDayOfMonth());
        calendar.set(Calendar.HOUR, Calendar.HOUR_OF_DAY); // ????????????????????????????????????????????
        return calendar.getTime();
    }

    private void initView(View view) {
        note_id = view.findViewById(R.id.tv_id_value);
        note_title = view.findViewById(R.id.textInputEditText_title);
        note_description = view.findViewById(R.id.textInputEditText_description);
        note_datePicker = view.findViewById(R.id.datePicker_input);
    }

    private void fillView() {
        note_id.setText(String.valueOf(noteCard.getId()));              // TODO next id
        note_title.setText(noteCard.getTitle());
        note_description.setText(noteCard.getDescription());
        initDatePicker(noteCard.getDateTime());
    }

    private void initDatePicker(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        this.note_datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }
}