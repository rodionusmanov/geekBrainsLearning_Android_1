package com.example.android_1_03;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class NotesListFragment extends Fragment {
    public static ArrayList<Note> notes = new ArrayList<>();
    private static boolean isNewNote = false;
    private int notesSize = 0;

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
        notesSize = notes.size();
        if (notesSize == 0) {
            FirstNote();
        }

        Bundle bundle = new Bundle();
        Bundle backBundle = this.getArguments();
        if (backBundle != null) {
            int index = backBundle.getInt("BackIndex");
            Note note = backBundle.getParcelable("BackNote");
            isNewNote = backBundle.getBoolean("BackCheckNew");
            if (backBundle.getBoolean("CheckDelete")) {
                notes.remove(index);
            } else if (isNewNote) {
                notes.remove(index);
                notes.add(note);
            } else {
                notes.set(index, note);
            }
        }


        createNoteButton = view.findViewById(R.id.create_note_button);
        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNewNote = true;
                notesSize = notes.size();
                Note note = new Note(" ", " ", " ", notes.size());
                notes.add(note);
                Bundle enterBundle = new Bundle();
                enterBundle.putInt("Index", notesSize);
                enterBundle.putBoolean("CheckNewNote", isNewNote);
                enterBundle.putParcelable("Note", note);
                showNoteExtendFragment(enterBundle);
            }
        });

        for (int i = 0; i < notes.size(); i++) {
            TextView tv = new TextView(getContext());
            tv.setText(notes.get(i).getNoteName());
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int POSITION = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isNewNote = false;
                    Note note = notes.get(POSITION);
                    Bundle enterBundle = new Bundle();
                    enterBundle.putInt("Index", POSITION);
                    enterBundle.putBoolean("CheckNewNote", isNewNote);
                    enterBundle.putParcelable("Note", note);
                    showNoteExtendFragment(enterBundle);
                }
            });
        }
    }


    private void showNoteExtendFragment(Bundle bundle) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            showPortrait(bundle);
        } else {
            showLandscape(bundle);
        }

    }

    private void showLandscape(Bundle bundle) {
        NoteExtendFragment noteExtendFragment = NoteExtendFragment.newInstance(bundle);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_right, noteExtendFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }

    private void showPortrait(Bundle bundle) {
        NoteExtendFragment noteExtendFragment = NoteExtendFragment.newInstance(bundle);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, noteExtendFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }

    private void FirstNote() {
        Date firstDate = new Date();
        Note firstNote = new Note("First note", "First description",
                String.valueOf(firstDate), notesSize);
        notes.add(firstNote);
        notesSize++;
    }

}