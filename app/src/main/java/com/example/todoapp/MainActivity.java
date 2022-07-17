package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            ListOfTitlesFragment listOfTitlesFragment = new ListOfTitlesFragment();
            FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction
                    .add(R.id.main_container, listOfTitlesFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // ПРИМЕНЯЕМ МЕНЮ (НАДУВАЕМ)
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Обрабатываем события по нажатиям на пункты МЕНЮ

        int id_item_menu = item.getItemId();

        switch (id_item_menu) {
            case R.id.menu_action_about:
                AboutFragment aboutFragment = new AboutFragment();
                FragmentManager fm = getSupportFragmentManager();
                fm
                        .beginTransaction()
                        .addToBackStack("")
                        .add(R.id.main_container, aboutFragment)
                        .commit();
                break;
            case R.id.menu_action_exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
