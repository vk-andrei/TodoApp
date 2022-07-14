package com.example.todoapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Note implements iNote, Parcelable {

    private static final Random random = new Random(); // ????
    // статический массив - когда мы запускаем прилож, мы в сатическом инициализаторе инициализируем
    // массив из 10 заметок. Проходим по кажд элементу массива и через ФАБРИЧНЫЙ МЕТОД инициализируем
    // каждую заметку. И, благодаря, итератору i мы обзываем кажд заметку. (заметка 1, заметка 2 ...)
    private static Note[] notes;
    private String title;
    private String description;
    private LocalDateTime dateTime;

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale")
    public static Note getNote(int index) {
        String title = String.format("Note %d", index);
        String description = String.format("Description of note %d", index);
        LocalDateTime dateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateTime = LocalDateTime.now().plusDays(-random.nextInt(5));
        }
        return new Note(title, description, dateTime);
    }

    /**** FROM PARCELABLE ****/
    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getTitle());
        parcel.writeString(getDescription());
        parcel.writeSerializable(getDateTime());
    }

    // Вспомогательный конструктор для заполнения ОБЪЕКТА соответствующими значениями
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        dateTime = (LocalDateTime) in.readSerializable();

    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
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