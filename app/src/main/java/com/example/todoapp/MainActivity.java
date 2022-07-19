package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar(isLandscape());

        if (savedInstanceState == null) {
            ListOfTitlesFragment listOfTitlesFragment = new ListOfTitlesFragment();
            FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction
                    .add(R.id.main_container, listOfTitlesFragment)
                    .commit();
        }
    }

    // Инициализация своего КАСТОМНОГО ТУЛБАРА
    private void initToolBar(boolean isLandscape) {
        Toolbar toolbar = findViewById(R.id.mine_toolbar);
        setSupportActionBar(toolbar);
        // DRAWER зависит от TOOLBAR поэтому засунем ему тулбар
        if (!isLandscape) {
            initDrawerToolBar(toolbar);
        }
    }

    private void initDrawerToolBar(Toolbar toolbar) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // связываем ЭТИ два объекта
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.drawer_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_drawer_about:
                        openAboutFragment();
                        return true;
                    case R.id.action_drawer_exit:
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private void openAboutFragment() {
        AboutFragment aboutFragment = new AboutFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm
                .beginTransaction()
                .addToBackStack("")
                .add(R.id.main_container, aboutFragment)
                .commit();
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
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
                openAboutFragment();
                break;
            case R.id.menu_action_exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
