package com.example.todoapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Note implements Parcelable, iNote {

    // статический массив - когда мы запускаем прилож, мы в сатическом инициализаторе инициализируем
    // массив из 10 заметок. Проходим по кажд элементу массива и через ФАБРИЧНЫЙ МЕТОД инициализируем
    // каждую заметку. И, благодаря, итератору i мы обзываем кажд заметку. (заметка 1, заметка 2 ...)
    //private static Note[] notes;
    private static final ArrayList<Note> notes;
    private String title;
    private String description;
    private LocalDateTime dateTime;

    private int id;
    private static int counter; // СТАТИЧЕСКИЙ СЧЕТЧИК

    public int getId() {
        return id;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Note(String title, String description, LocalDateTime dateTime) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
    }

    // Нестатический инициализатор:
    {   // Кажд раз перед тем как мы инициализируем конструктор КЛАССА, мы идентифицируем наш идентификатор.
        // Он при передаче посылки СОХРАНИТСЯ! (идентификаторы НЕ ПОПЛЫВУТ)
        id = ++counter;
    }

    // статический блок инициализации:
    // отрабатывает 1 раз перед стартом В ПЕРВУЮ ОЧЕРЕДЬ
    static {
        notes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            notes.add(Note.getNote(i));
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

    public static ArrayList<Note> getNotes() {
        return notes;
    }

    // ФАБРИЧНЫЙ МЕТОД ПОЛУЧЕНИЯ ОБЪЕКТА: он возвращает NOTE
    // на вход ему приходит какой-то индекс
    @SuppressLint("DefaultLocale")
    public static Note getNote(int index) {
        String title = String.format("Note %d", index);
        String description = String.format("Description of note %d", index);
        LocalDateTime dateTime = LocalDateTime.now();
        return new Note(title, description, dateTime);
    }

    /**** PARCELABLE ****/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getTitle());
        parcel.writeString(getDescription());
        parcel.writeSerializable(getDateTime());
    }

    // Вспомогательный конструктор для заполнения ОБЪЕКТА соответствующими значениями
    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        dateTime = (LocalDateTime) in.readSerializable();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
    /*****************************************/

}