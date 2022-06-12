package com.example.android_1_03.items;

import com.example.android_1_03.Note;

import java.util.ArrayList;
import java.util.List;

public interface IDataNoteSource {
    ArrayList<Note> getNotes();
    void deleteNote(int position);
}
