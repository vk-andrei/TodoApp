package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import static com.example.todoapp.NoteDetailsFragment.SELECTED_INDEX;


public class NoteDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        // Эта активити для ландшафтного дизайна. По-этому при переходе в лэндскейп нам надо убить
        // эту активити
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        }

        // если же мы в портрейном режиме - проверим создавали ли мы фрагмент здесь?
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container_IN_NEW_NOTE_DETAILS_ACTIVITY,
                            NoteDetailsFragment.newInstance(getIntent().getExtras().getInt(SELECTED_INDEX)))
                    .commit();


        }




    }






}