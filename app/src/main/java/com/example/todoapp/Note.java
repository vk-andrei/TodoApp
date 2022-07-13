package com.example.todoapp;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.Random;


public class Note implements iNote {

    private static final Random random = new Random(); // ????
    // статический массив - когда мы запускаем прилож, мы в сатическом инициализаторе инициализируем
    // массив из 10 заметок. Проходим по кажд элементу массива и через ФАБРИЧНЫЙ МЕТОД инициализируем
    // каждую заметку. И, благодаря, итератору i мы обзываем кажд заметку. (заетка 1, заметка 2 ...)
    private static Note[] notes;
    private String title;
    private String description;
    private LocalDateTime dateTime;

    @Override
    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public Note setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Note setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    // статический блок инициализации:
    // отрабатывает 1 раз перед стартом В ПЕРВУЮ ОЧЕРЕДЬ
    static {
        notes = new Note[10];
        for (int i = 0; i < notes.length; i++) {
            notes[i] = Note.getNote(i);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public static Note[] getNotes() {
        return notes;
    }

    // ФАБРИЧНЫЙ МЕТОД ПОЛУЧЕНИЯ ОБЪЕКТА: он возвращает NOTE
    // на вход ему приходит какой-то индекс
    @SuppressLint("DefaultLocale")
    public static Note getNote(int index) {

        String title = String.format("Note %d", index);
        String description = String.format("Description of note %d", index);
        LocalDateTime dateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateTime = LocalDateTime.now().plusDays(-random.nextInt(5));
        }
        return new Note().setTitle(title).setDescription(description).setDate(dateTime);
    }
}