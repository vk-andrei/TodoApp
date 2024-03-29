package com.example.todoapp.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.todoapp.R;
import com.example.todoapp.observe.Publisher;
import com.example.todoapp.ui.ListOfTitlesFragment;
import com.example.todoapp.util.DialogAlertExitFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Navigation navigation;
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = new Navigation(getSupportFragmentManager());
        initToolBar(isLandscape());

        getNavigation().addFragment(ListOfTitlesFragment.newInstance(), false);

/*
        if (savedInstanceState == null) {
            Log.d("TAG", "MainActivity: onCreate: create ListOfFragments");
            /*ListOfTitlesFragment listOfTitlesFragment = new ListOfTitlesFragment();
            FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction
                    .add(R.id.main_container, listOfTitlesFragment)
                    .commit();
            getNavigation().addFragment(ListOfTitlesFragment.newInstance(), false);
        }*/
    }

    // Инициализация своего КАСТОМНОГО ТУЛБАРА
    private void initToolBar(boolean isLandscape) {
        Toolbar toolbar = findViewById(R.id.mine_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ЧТО ЭТО ДЕЛАЕТ?
        getSupportActionBar().setHomeButtonEnabled(true);      // ЧТО ЭТО ДЕЛАЕТ?
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

    public void openFindNote() {
        Toast.makeText(this, "TODO FIND Note", Toast.LENGTH_SHORT).show();
    }

    public void openSettingsFragment() {
        Toast.makeText(this, "TODO SETTINGS fragment", Toast.LENGTH_SHORT).show();
    }

    //TODO переделать под NAVIGATION. ----->  DONE
    public void openAboutFragment() {
        AboutFragment aboutFragment = new AboutFragment();
        /*FragmentManager fm = getSupportFragmentManager();
        fm
                .beginTransaction()
                .addToBackStack("")
                .add(R.id.main_container, aboutFragment)
                .commit();*/
        getNavigation().addFragment(aboutFragment, true);
    }

    private void addNewNote() {
        Toast.makeText(this, "TODO adding a new note", Toast.LENGTH_SHORT).show();
    }

    public void share() {
        Toast.makeText(this, "TODO SHARE a note", Toast.LENGTH_SHORT).show();
    }

/*

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
*/

    // ДЛЯ ЧЕГО ????????????????????????
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

}
