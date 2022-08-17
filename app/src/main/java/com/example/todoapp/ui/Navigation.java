package com.example.todoapp.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.todoapp.R;

public class Navigation {
    // Выделяем отдел. класс навигации, чтобы разгрузить активити. Туда перенесём создание фрагмента.
    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment, boolean useBackStack) {
        // Open transaction
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.main_container, fragment);
        // Первый фрагмент не будем записывать в стек обратного вызова, чтобы по кнопке "Назад"
        // просто выйти из приложения.
        if (useBackStack) {
            ft.addToBackStack("");
        }
        ft.commit();
    }


}
