package com.example.android_1_03;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    String noteName;
    String noteDescription;
    String noteCreationDate;
    int index;
    boolean favorite;


    public Note(String noteName, String noteDescription, String noteCreationDate, int index) {
        this.noteName = noteName;
        this.noteDescription = noteDescription;
        this.noteCreationDate = noteCreationDate;
        this.index = index;
        this.favorite = false;
    }

    protected Note(Parcel in) {
        noteName = in.readString();
        noteDescription = in.readString();
        noteCreationDate = in.readString();
        index = in.readInt();
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

    public String getNoteName() {
        return noteName;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public String getNoteCreationDate() {
        return noteCreationDate;
    }

    public int getIndex() {
        return index;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public void setNoteCreationDate(String noteCreationDate) {
        this.noteCreationDate = noteCreationDate;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(noteName);
        parcel.writeString(noteDescription);
        parcel.writeString(noteCreationDate);
        parcel.writeInt(index);
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

}
