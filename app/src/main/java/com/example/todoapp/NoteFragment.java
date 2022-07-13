package com.example.todoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoteFragment extends Fragment {

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
        initNotes(view);
    }

    private void initNotes(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        // создаём список заметок на экране из массива в ресурсах
        String[] notes = getResources().getStringArray(R.array.notes_array);
        // В этом цикле создаём элемент TextView,
        // заполняем его значениями и добавляем на экран.
        for (String note : notes) {
            TextView textView = new TextView(getContext());
            textView.setText(note);
            textView.setTextSize(25);
            linearLayout.addView(textView);
        }
    }
}