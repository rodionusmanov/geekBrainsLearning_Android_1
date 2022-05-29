package com.example.android_1_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView startTV = findViewById(R.id.start_text);
        ImageView startIV = findViewById(R.id.start_picture);
        FrameLayout startFL = findViewById(R.id.start_pictire_background);

        startIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initNotesApp();
                startFL.setVisibility(View.GONE);
                startTV.setVisibility(View.GONE);
                startIV.setVisibility(View.GONE);
            }
        });

        startTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initNotesApp();
                startFL.setVisibility(View.GONE);
                startTV.setVisibility(View.GONE);
                startIV.setVisibility(View.GONE);
            }
        });

        startFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initNotesApp();
                startFL.setVisibility(View.GONE);
                startTV.setVisibility(View.GONE);
                startIV.setVisibility(View.GONE);
            }
        });

        /*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            NoteExtendFragment nef = NoteExtendFragment.newInstance(0);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_right, nef)
                    .addToBackStack(null)
                    .commit();
        }*/
    }

    private void initNotesApp() {
        NotesListFragment notesListFragment = new NotesListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, notesListFragment)
                .commit();
    }


}