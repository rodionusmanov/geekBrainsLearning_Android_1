package com.example.android_1_03;

public class Note {
    String noteName;
    String noteDescription;
    String noteCreationDate;
    int index;

    public Note(String noteName, String noteDescription, String noteCreationDate, int index) {
        this.noteName = noteName;
        this.noteDescription = noteDescription;
        this.noteCreationDate = noteCreationDate;
        this.index = index;
    }

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
}
