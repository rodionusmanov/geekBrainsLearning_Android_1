package com.example.android_1_03;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class ThemeSwitcherFragment extends Fragment {
    private static int themeId = 0;
    private static final String NameSharedPreference = "LOGIN";
    private static final String APP_THEME = "APP_THEME";
    private RadioButton defaultRB;
    private RadioButton dayRB;
    private RadioButton nightRB;
    private RadioButton unDefaultRB;
    private Button getThemeButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.theme_switcher, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTheme(codeStyleToStyleId(themeId));
        defaultRB = view.findViewById(R.id.radio_default_theme);
        dayRB = view.findViewById(R.id.radio_day_theme);
        nightRB = view.findViewById(R.id.radio_night_theme);
        unDefaultRB = view.findViewById(R.id.radio_undefault_theme);
        Button getThemeButton = view.findViewById(R.id.applyThemeButton);

        Bundle themeBundle = getArguments();
        if (themeBundle != null) {
            themeId = themeBundle.getInt("ThemeId");        }


        checkRadioButtonPosition(themeId);

        defaultRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeId = 0;
                requireActivity().setTheme(codeStyleToStyleId(0));
                redraw();
            }
        });

        dayRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeId = 1;
                requireActivity().setTheme(codeStyleToStyleId(1));
                redraw();
            }
        });

        nightRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeId = 2;
                requireActivity().setTheme(codeStyleToStyleId(2));
                redraw();
            }
        });

        unDefaultRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themeId = 3;
                requireActivity().setTheme(codeStyleToStyleId(3));
                redraw();
            }
        });

        getThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotesListFragment notesListFragment = new NotesListFragment();
                Bundle backBundle = new Bundle();
                backBundle.putInt("ThemeIndex", themeId);
                notesListFragment.setArguments(themeBundle);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, notesListFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void redraw() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, new ThemeSwitcherFragment())
                .addToBackStack(null)
                .commit();
    }

    private void checkRadioButtonPosition(int themeId) {
        switch (themeId) {
            case (0):
                defaultRB.setChecked(true);
                return;
            case (1):
                dayRB.setChecked(true);
                return;
            case (2):
                nightRB.setChecked(true);
                return;
            case (3):
                unDefaultRB.setChecked(true);
                return;
            default:
        }
    }

    private int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case (0):
                return R.style.Theme_Android_1_03;
            case (1):
                return R.style.DayTheme;
            case (2):
                return R.style.NightTheme;
            case (3):
                return R.style.UnDefaultTheme;
            default:
                return R.style.Theme_Android_1_03;
        }
    }
}