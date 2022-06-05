package com.example.android_1_03;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android_1_03.items.NoteAdapter;
import com.example.android_1_03.items.NotesClickListener;

import java.util.ArrayList;
import java.util.Date;

public class NotesListFragment extends Fragment {
    private static String addRemoveFavorites = "Добавить в избранное";
    private static final int CONTEXT_MENU_FAVORITES = 1;
    private static final int CONTEXT_MENU_DELETE = 2;
    public static ArrayList<Note> notes = new ArrayList<>();
    private static boolean isNewNote = false;
    private int notesSize = 0;
    private int indexToDelete = 0;
    private static boolean firstLaunch = true;
    private static boolean formationList = true;
    private static boolean rebuild;

    Button createNoteButton;
    Button formationSwitchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBar();
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    private void setActionBar() {
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity)
                requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(R.string.main_list);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        initList(view);
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        formationSwitchButton = view.findViewById(R.id.formation_switch_button);
        Bundle backBundle = this.getArguments();

        notesSize = notes.size();
        if (firstLaunch && backBundle == null) {
            firstLaunch = false;
            FirstNote();

        }


        if (backBundle != null) {
            int index = backBundle.getInt("BackIndex");
            Note note = backBundle.getParcelable("BackNote");
            isNewNote = backBundle.getBoolean("BackCheckNew");
            if (backBundle.getBoolean("Rebuild")) {
                isNewNote = true;
                index = notes.size();
            }
            rebuild = backBundle.getBoolean("Rebuild");
            if (backBundle.getBoolean("CheckDelete")) {
                notes.remove(index);
                notesSize = notes.size();
            } else if (isNewNote) {
                if (!backBundle.getBoolean("Rebuild")) {
                    notes.remove(index);
                }
                notes.add(note);
            } else {
                notes.set(index, note);
            }
            refreshNoteIndex();
        }


        String[] names = new String[notes.size()];

        ArrayList<Note> list = new ArrayList();

        for (int i = 0; i < notesSize; i++) {
            list.add(new Note(notes.get(i).noteName, notes.get(i).noteDescription, notes.get(i).noteCreationDate, notes.get(i).index));
            names[i] = notes.get(i).noteName;
        }
        RecyclerView rv = requireActivity().findViewById(R.id.rvNotes);
        if (formationList) {
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            rv.setLayoutManager(llm);
        } else {
            GridLayoutManager llm = new GridLayoutManager(getContext(), 2);
            rv.setLayoutManager(llm);
        }


        NoteAdapter adapter = new NoteAdapter();
        adapter.setList(list);
        rv.setAdapter(adapter);
//        registerForContextMenu(rv);

        adapter.setListener(new NotesClickListener() {
            @Override
            public void onTextViewClick(int position) {
                isNewNote = false;
                Note note = notes.get(position);
                Bundle enterBundle = new Bundle();
                enterBundle.putInt("Index", position);
                enterBundle.putBoolean("CheckNewNote", isNewNote);
                enterBundle.putParcelable("Note", note);
                showNoteExtendFragment(enterBundle);
            }
        });


        formationSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                formationList = !formationList;
                intent.putExtra("StartScreenWorked", true);
                requireActivity().finish();
                startActivity(intent);
            }
        });
        /* for (int i = 0; i < notes.size(); i++) {

         *//*TextView tv = new TextView(getContext());
            tv.setText(notes.get(i).getNoteName());
            tv.setTextSize(30);
            layoutView.addView(tv);*//*

         *//*View item = getLayoutInflater().inflate(R.layout.item, layoutView, false);
            TextView tv = item.findViewById(R.id.itemTextView);
            tv.setText(notes.get(i).noteName);
            layoutView.addView(item);
            final int POSITION = i;

            registerForContextMenu(tv);
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
            });*//*
        }*/

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
    }

    private void refreshNoteIndex() {
        int index = 0;
        for (Note note : notes) {
            note.index = index;
            index++;
        }
        if (rebuild) {
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            intent.putExtra("StartScreenWorked", true);
            requireActivity().finish();
            startActivity(intent);
        }
    }

    /*public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        TextView tv = (TextView) v;
        indexToDelete = findNoteByName(tv.getText());

        menu.add(0, CONTEXT_MENU_FAVORITES, 0, addRemoveFavorites);
        menu.add(0, CONTEXT_MENU_DELETE, 0, "Удалить заметку");
    }*/

    private int findNoteByName(CharSequence text) {
        for (Note note : notes) {
            if (note.noteName.equals(text)) {
                return note.index;
            }
        }
        return 0;
    }

    public boolean onContextItemSelected(MenuItem item) {

        Toast.makeText(requireContext(), String.valueOf(indexToDelete), Toast.LENGTH_SHORT).show();

        item.getMenuInfo();
        switch (item.getItemId()) {
            case CONTEXT_MENU_FAVORITES:
//                tv.setTex
                break;
            case CONTEXT_MENU_DELETE:
                deletingNote(indexToDelete);
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