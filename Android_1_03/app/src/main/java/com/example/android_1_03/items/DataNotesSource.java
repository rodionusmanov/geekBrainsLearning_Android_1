package com.example.android_1_03.items;

import com.example.android_1_03.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataNotesSource implements IDataNoteSource {
    private ArrayList<Note> list = new ArrayList<>();

    public DataNotesSource() {
        initList();
    }

    private void initList() {
        Note note = new Note("Example note", "Example description",
                "", 0);
        list.add(note);
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
