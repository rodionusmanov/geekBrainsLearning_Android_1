package com.example.android_1_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public static ArrayList<Note> notes = new ArrayList<>();
    public static boolean isNewNote = false;
    public static int currentIndex = 0;
    public static int noteCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotesListFragment notesListFragment = new NotesListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, notesListFragment)
                .commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            NoteExtendFragment nef = NoteExtendFragment.newInstance(0);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_right, nef)
                    .addToBackStack(null)
                    .commit();
        }
    }
}