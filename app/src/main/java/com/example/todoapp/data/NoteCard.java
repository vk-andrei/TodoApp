package com.example.todoapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class NoteCard implements Parcelable {

    //private static int count = 0;
    private int id;
    private String title;
    private String description;
    private Date dateTime;


/*    public NoteCard () {
        this.id = count++;
    }*/


    public NoteCard(int id, String title, String description, Date dateTime) {
        this.id = id;
        //this();
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
    }

    /**** PARCELABLE start ***/
    protected NoteCard(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        dateTime = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(dateTime.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteCard> CREATOR = new Creator<NoteCard>() {
        @Override
        public NoteCard createFromParcel(Parcel in) {
            return new NoteCard(in);
        }

        @Override
        public NoteCard[] newArray(int size) {
            return new NoteCard[size];
        }
    };

    /**** PARCELABLE end ***/

    @NonNull
    @Override
    public String toString() {
        return String.format("Note - id: %s, title: %s, description: %s, date: %s", id, title, description, dateTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTime() {
        return dateTime;
    }
}