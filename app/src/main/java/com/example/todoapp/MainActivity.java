package com.example.todoapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;


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


        drawerLayout = findViewById(R.id.drawer_layout);
        // Создаем ActionBarDrawerToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // связываем ЭТИ два объекта
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.drawer_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout = findViewById(R.id.drawer_layout);
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_drawer_add_new_note:
                        addNewNote();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.action_drawer_settings:
                        openSettingsFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.action_drawer_about:
                        openAboutFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.action_drawer_exit:

                        //showAlertDialogExit();
                        showAlertDialogExitFragment();

                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.action_drawer_share:
                        share();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }
                return false;
            }
        });
    }

    // OLD
    private void showAlertDialogExit() {
        new AlertDialog.Builder(this)
                .setTitle("Alert!")
                .setMessage("Do you really want to Exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }

    private void showAlertDialogExitFragment() {
        new DialogAlertExitFragment().show(getSupportFragmentManager(), "TAG");
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    // МЕНЮ СПРАВА (три точки)

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

        int idItemMenu = item.getItemId();

        switch (idItemMenu) {
            case R.id.menu_add_new_note:
                addNewNote();
                return true;
            case R.id.menu_action_settings:
                openSettingsFragment();
                return true;
            case R.id.menu_action_about:
                openAboutFragment();
                return true;
            case R.id.menu_action_find:
                openFindNote();
                return true;
            case R.id.menu_action_exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFindNote() {
        Toast.makeText(this, "TODO FIND Note", Toast.LENGTH_SHORT).show();
    }

    private void openSettingsFragment() {
        Toast.makeText(this, "TODO SETTINGS fragment", Toast.LENGTH_SHORT).show();
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

    private void addNewNote() {
        Toast.makeText(this, "TODO adding a new note", Toast.LENGTH_SHORT).show();
    }

    private void share() {
        Toast.makeText(this, "TODO SHARE a note", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        drawerLayout = findViewById(R.id.drawer_layout);
        // TODO solve problem here
        // java.lang.NullPointerException: Attempt to invoke virtual method
        // 'boolean androidx.drawerlayout.widget.DrawerLayout.isDrawerOpen(int)' on a null object reference
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
