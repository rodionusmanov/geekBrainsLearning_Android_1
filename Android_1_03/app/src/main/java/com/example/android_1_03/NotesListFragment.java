package com.example.android_1_03;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class NotesListFragment extends Fragment {
    private static String addRemoceFavorites = "Добавить в избранное";
    private static final int CONTEXT_MENU_FAVORITES = 1;
    private static final int CONTEXT_MENU_DELETE = 2;
    public static ArrayList<Note> notes = new ArrayList<>();
    private static boolean isNewNote = false;
    private int notesSize = 0;

    Button createNoteButton;
    private int contextIndex;

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
            contextIndex = i;
            registerForContextMenu(tv);
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

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, CONTEXT_MENU_FAVORITES, 0, addRemoceFavorites);
        menu.add(0, CONTEXT_MENU_DELETE, 0, "Удалить заметку");
    }

    public boolean onContextItemSelected(MenuItem item) {
        item.getMenuInfo();
        switch (item.getItemId()) {
            case CONTEXT_MENU_FAVORITES:
//                tv.setTex
                break;
            case CONTEXT_MENU_DELETE:
//                deletingNote(index);
                break;

        }
        return super.onContextItemSelected(item);
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


    public void deletingNote(int index) {
        NotesListFragment notesListFragment = new NotesListFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        Bundle deleteBundle = new Bundle();
        deleteBundle.putBoolean("CheckDelete", true);
        deleteBundle.putInt("BackIndex", index);
        notesListFragment.setArguments(deleteBundle);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, notesListFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }


}