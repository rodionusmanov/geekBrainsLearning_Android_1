package com.example.android_1_03.items;

import com.example.android_1_03.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataNotesSource implements IDataNoteSource{
    private ArrayList<Note> list = new ArrayList<>();

    public DataNotesSource() {
        initList();
    }

    private void initList() {
        for (int i = 0; i < 5; i++) {
            int size = 1;
            Date firstDate = new Date();
            Note note = new Note(String.valueOf(i) + " note", String.valueOf(i) + " description",
                    String.valueOf(firstDate), i);
            list.add(note);
        }
    }

    @Override
    public ArrayList<Note> getNotes() {
        return list;
    }

    @Override
    public void deleteNote(int position) {
        list.remove(position);
    }
}
