package com.example.android_1_03;

import android.app.Activity;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android_1_03.items.DataNotesSource;
import com.example.android_1_03.items.IDataNoteSource;
import com.example.android_1_03.items.NoteAdapter;
import com.example.android_1_03.items.NotesClickListener;

import java.util.ArrayList;

public class NotesListFragment extends Fragment {
    private static String addRemoveFavorites = "Добавить в избранное";
    private static final int CONTEXT_MENU_FAVORITES = 1;
    private static final int CONTEXT_MENU_DELETE = 2;
    public static ArrayList<Note> list = new ArrayList<>();
    private static boolean isNewNote = false;
    private int indexToDelete = 0;
    private static boolean firstLaunch = true;
    private static boolean formationList = true;
    private static boolean rebuild;

    private Note note = null;
    private IDataNoteSource dataNoteSource = new DataNotesSource();

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

        if (firstLaunch && backBundle == null) {
            firstLaunch = false;
            list = dataNoteSource.getNotes();
        }


        if (backBundle != null) {
            int index = backBundle.getInt("BackIndex");
            Note note = backBundle.getParcelable("BackNote");
            isNewNote = backBundle.getBoolean("BackCheckNew");
            if (backBundle.getBoolean("Rebuild")) {
                isNewNote = true;
                index = list.size();
            }
            rebuild = backBundle.getBoolean("Rebuild");
            if (backBundle.getBoolean("CheckDelete")) {
                list.remove(index);
            } else if (isNewNote) {
                if (!backBundle.getBoolean("Rebuild")) {
                    list.remove(index);
                }
                list.add(note);
            } else {
                list.set(index, note);
            }
            refreshNoteIndex();
        }


        String[] names = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            names[i] = list.get(i).noteName;
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

//        registerForContextMenu(rv);

        adapter.setListener(new NotesClickListener() {

            @Override
            public void onTextViewClick(int position) {
//                Toast.makeText(getContext(), "show " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                isNewNote = false;
                Note note = list.get(position);
                Bundle enterBundle = new Bundle();
                enterBundle.putInt("Index", position);
                enterBundle.putBoolean("CheckNewNote", isNewNote);
                enterBundle.putParcelable("Note", note);
                showNoteExtendFragment(enterBundle);
            }

            @Override
            public void onLongItemClick(int position) {
//                Toast.makeText(getContext(), "show long " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                Activity activity = requireActivity();
                TextView tv = (TextView) view.findViewById(R.id.itemTextView);
                PopupMenu popupMenu = new PopupMenu(activity, tv);
                activity.getMenuInflater().inflate(R.menu.context_menu_for_note, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_add_remove_favorites:
                                list.get(position).favorite = !list.get(position).favorite;
                                rv.setAdapter(adapter);
                                return true;
                            case R.id.action_delete_note:
                                list.remove(position);
                                rv.setAdapter(adapter);
                                return true;
                        }
                        return true;
                    }
                });
            }
        });


        rv.setAdapter(adapter);

        formationSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                formationList = !formationList;;
                intent.putExtra("StartScreenWorked", true);
                requireActivity().finish();
                startActivity(intent);
            }
        });


        createNoteButton = view.findViewById(R.id.create_note_button);
        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNewNote = true;
                Note note = new Note("", "", "", list.size());
                list.add(note);
                Bundle enterBundle = new Bundle();
                enterBundle.putInt("Index", list.size() - 1);
                enterBundle.putBoolean("CheckNewNote", isNewNote);
                enterBundle.putParcelable("Note", note);
                showNoteExtendFragment(enterBundle);
            }
        });
    }

 /*   @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_for_note, menu);
    }*/

 /*   @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_remove_favorites:
                return true;
            case R.id.action_delete_note:
                return true;
        }

        return super.onContextItemSelected(item);
    }*/

    private void refreshNoteIndex() {
        int index = 0;
        for (Note note : list) {
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


    /*private int findNoteByName(CharSequence text) {
        for (Note note : list) {
            if (note.noteName.equals(text)) {
                return note.index;
            }
        }
        return 0;
    }
*/

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