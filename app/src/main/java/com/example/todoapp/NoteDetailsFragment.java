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
import android.widget.TextView;

public class NoteDetailsFragment extends Fragment {

    static final String SELECTED_INDEX = "index";

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
        if (arguments != null) {
            int index = arguments.getInt(SELECTED_INDEX);

            TextView tV_title = view.findViewById(R.id.fragment_note_title);

            tV_title.setText(Note.getNotes()[index].getTitle());
            // CHANGING OUR TITLE???
            tV_title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Note.getNotes()[index].setTitle(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            TextView tV_description = view.findViewById(R.id.fragment_note_description);
            tV_description.setText(Note.getNotes()[index].getDescription());

            TextView tV_data = view.findViewById(R.id.fragment_note_data);
            //tV_data.setText(Note.getNotes()[index].getDateTime());
        }

    }

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы
    public static NoteDetailsFragment newInstance(int index) {
        // Создание фрагмента
        NoteDetailsFragment fragment = new NoteDetailsFragment();

        // Передача параметра через бандл
        Bundle args = new Bundle();
        args.putInt(SELECTED_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

}