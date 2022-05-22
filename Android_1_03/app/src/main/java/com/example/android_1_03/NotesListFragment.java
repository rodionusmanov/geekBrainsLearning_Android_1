package com.example.android_1_03;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

public class NotesListFragment extends Fragment {

    Button createNoteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;

        if (MainActivity.noteCounter == 0) {
            FirstNote();
        }

        createNoteButton = view.findViewById(R.id.create_note_button);
        createNoteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MainActivity.isNewNote = true;
                MainActivity.currentIndex = MainActivity.noteCounter;
                showNoteExtendFragment(MainActivity.noteCounter);
            }
        });

        for (int i = 0; i < MainActivity.notes.size(); i++) {
            Note tempNote = MainActivity.notes.get(i);
            TextView tv = new TextView(getContext());
            tv.setText(tempNote.getNoteName());
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int POSITION = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.isNewNote = false;
                    MainActivity.currentIndex = POSITION;
                    showNoteExtendFragment(POSITION);
                }
            });
        }
    }

    private void FirstNote() {
        Date firstDate = new Date();
        Note firstNote = new Note("First note", "First description", String.valueOf(firstDate), MainActivity.currentIndex);
        MainActivity.notes.add(firstNote);
        MainActivity.noteCounter++;
    }

    private void showNoteExtendFragment(int position) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            showPortrait(position);
        } else {
            showLandscape(position);
        }

    }

    private void showLandscape(int position) {
        NoteExtendFragment noteExtendFragment = NoteExtendFragment.newInstance(position);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_right, noteExtendFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }

    private void showPortrait(int position) {
        NoteExtendFragment noteExtendFragment = NoteExtendFragment.newInstance(position);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, noteExtendFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }
}