package com.example.android_1_03;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NoteExtendFragment extends Fragment {

    static private String ARG_INDEX = "index";
    int noteIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_extended, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();

        TextView nameTextView = view.findViewById(R.id.note_name);
        TextView descriptionTextView = view.findViewById(R.id.note_description);
        TextView dateTextView = view.findViewById(R.id.note_date);
        TextView indexTextView = view.findViewById(R.id.note_index);

        DatePicker datePicker = view.findViewById(R.id.datePicker);
        Button datePick = view.findViewById(R.id.pick_date);
        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder dateBuilder = new StringBuilder().append(datePicker.getDayOfMonth()).append(".").append(datePicker.getMonth() + 1).append(".").append(datePicker.getYear());
            }
        });

        Button saveButton;
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.isNewNote) {
                    noteIndex = MainActivity.noteCounter;
                    MainActivity.noteCounter++;
                } else {
                    noteIndex = MainActivity.currentIndex;
                }
                StringBuilder dateBuilder = new StringBuilder().append(datePicker.getDayOfMonth()).append(".").append(datePicker.getMonth() + 1).append(".").append(datePicker.getYear());
                Note note = new Note(String.valueOf(nameTextView.getText()),
                        String.valueOf(descriptionTextView.getText()),
                        String.valueOf(dateBuilder.toString()), noteIndex);
                if (MainActivity.isNewNote) {
                    MainActivity.notes.add(note);
                } else {
                    MainActivity.notes.set(MainActivity.currentIndex, note);
                }

                RefreshNotesList();
            }
        });



        /*if (arguments != null) {
            int index = arguments.getInt(ARG_INDEX);
            ImageView iv = view.findViewById(R.id.flag_image);
            TypedArray images = getResources().obtainTypedArray(R.array.notesNotes);
            iv.setImageResource(images.getResourceId(index, 0));
            images.recycle();
        }*/

        if (!MainActivity.isNewNote) {
            Note tempNote = MainActivity.notes.get(MainActivity.currentIndex);
            nameTextView.setTextSize(30);
            descriptionTextView.setTextSize(20);
            dateTextView.setTextSize(20);
            indexTextView.setTextSize(20);
            nameTextView.setText(tempNote.getNoteName());
            descriptionTextView.setText(tempNote.getNoteDescription());
            dateTextView.setText("Дата создания: " + tempNote.getNoteCreationDate());
            indexTextView.setText("Note index: " + String.valueOf(tempNote.getIndex()));

            Button deleteButton;
            deleteButton = view.findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.notes.remove(MainActivity.currentIndex);
                    RefreshNotesList();
                }
            });
        }
    }

    private void RefreshNotesList() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            NotesListFragment notesListFragment = new NotesListFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, notesListFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .commit();
        }
    }

    public static NoteExtendFragment newInstance(int index) {
        NoteExtendFragment fragment = new NoteExtendFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }
}
