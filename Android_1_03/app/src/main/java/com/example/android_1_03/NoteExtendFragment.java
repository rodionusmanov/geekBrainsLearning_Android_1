package com.example.android_1_03;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

public class NoteExtendFragment extends Fragment {

    static private String ARG_INDEX = "index";
    int noteIndex = 0;
    private boolean isNewNote;
    Note note = new Note(" ", " ", " ", noteIndex);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        setActionBar();
        return inflater.inflate(R.layout.fragment_note_extended, container, false);
    }

    private void setActionBar() {
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity)
                requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(R.string.note_extended);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null) {
            noteIndex = bundle.getInt("Index");
            isNewNote = bundle.getBoolean("CheckNewNote");
            note = bundle.getParcelable("Note");
        }

        TextView nameTextView = view.findViewById(R.id.note_name);
        TextView descriptionTextView = view.findViewById(R.id.note_description);
        TextView dateTextView = view.findViewById(R.id.note_date);
        TextView indexTextView = view.findViewById(R.id.note_index);

        nameTextView.setText(note.getNoteName());
        descriptionTextView.setText(note.getNoteDescription());
        dateTextView.setText(note.getNoteCreationDate());
        indexTextView.setText(String.valueOf(note.getIndex()));

        DatePicker datePicker = view.findViewById(R.id.datePicker);
        Button datePick = view.findViewById(R.id.pick_date);
        datePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder dateBuilder = new StringBuilder().append(datePicker.getDayOfMonth()).append(".").append(datePicker.getMonth() + 1).append(".").append(datePicker.getYear());
                dateTextView.setText(dateBuilder.toString());
            }
        });

        Button saveButton;
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(requireContext(), R.string.note_saved, Toast.LENGTH_SHORT).show();

                StringBuilder dateBuilder = new StringBuilder().append(datePicker.getDayOfMonth()).append(".").append(datePicker.getMonth() + 1).append(".").append(datePicker.getYear());
                Note note = new Note(String.valueOf(nameTextView.getText()),
                        String.valueOf(descriptionTextView.getText()),
                        dateBuilder.toString(), noteIndex);

                note.setNoteName(String.valueOf(nameTextView.getText()));
                note.setNoteDescription(String.valueOf(descriptionTextView.getText()));
                note.setNoteCreationDate(dateBuilder.toString());
                note.setIndex(noteIndex);
                Bundle backBundle = new Bundle();
                backBundle.putInt("BackIndex", noteIndex);
                backBundle.putBoolean("CheckDelete", false);
                backBundle.putBoolean("BackCheckNew", isNewNote);
                backBundle.putParcelable("BackNote", note);
                backBundle.putBoolean("Rebuild", false);
                RefreshNotesList(backBundle);
            }
        });

        Button deleteButton;
        deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle deleteBundle = new Bundle();
                deleteBundle.putInt("BackIndex", noteIndex);
                deleteBundle.putParcelable("BackNote", note);
                Note tempNoteToRebuild = note;
                Snackbar.make(view, R.string.note_deleted, Snackbar.LENGTH_LONG).setAction(R.string.note_rebuild, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        deleteBundle.putBoolean("BackCheckNew", isNewNote);
                        deleteBundle.putBoolean("CheckDelete", false);
                        deleteBundle.putBoolean("Rebuild", true);
                        RefreshNotesList(deleteBundle);
                    }
                }).show();

                deleteBundle.putBoolean("CheckDelete", true);
                deleteBundle.putInt("BackIndex", noteIndex);
                deleteBundle.putBoolean("Rebuild", false);
                RefreshNotesList(deleteBundle);
            }
        });

    }

    private void RefreshNotesList(Bundle bundle) {
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        NotesListFragment notesListFragment = new NotesListFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        notesListFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, notesListFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
//        }
    }

    public static NoteExtendFragment newInstance(Bundle bundle) {
        NoteExtendFragment fragment = new NoteExtendFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
