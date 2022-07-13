package com.example.todoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            NoteFragment noteFragment = new NoteFragment();
            FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction
                    .replace(R.id.main_container, noteFragment)
                    .commit();

        }


    }
}