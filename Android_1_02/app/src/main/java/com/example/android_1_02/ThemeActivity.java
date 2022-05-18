package com.example.android_1_02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ThemeActivity extends AppCompatActivity {
    private static final String NameSharedPreference = "LOGIN";
    private static final String APP_THEME = "APP_THEME";
    private RadioButton defaultThemeRadioButton;
    private RadioButton dayThemeRadioButton;
    private RadioButton nightThemeRadioButton;
    private RadioButton myThemeRadioButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(MainActivity.themeNumber));
        setContentView(R.layout.theme_activity);
        defaultThemeRadioButton = findViewById(R.id.radio_default_theme);
        dayThemeRadioButton = findViewById(R.id.radio_day_theme);
        nightThemeRadioButton = findViewById(R.id.radio_night_theme);
        myThemeRadioButton = findViewById(R.id.radio_my_theme);
        Button applyButton = findViewById(R.id.applyThemeButton);

        checkRadioButtonPosition(MainActivity.themeNumber);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent mainIntent = new Intent(ThemeActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        defaultThemeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.themeNumber = 0;
                setAppTheme(0);
            }
        });

        dayThemeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.themeNumber = 1;
                setAppTheme(1);
            }
        });

        nightThemeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.themeNumber = 2;
                setAppTheme(2);
            }
        });

        myThemeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.themeNumber = 3;
                setAppTheme(3);
            }
        });
    }

    private void checkRadioButtonPosition(int themeNumber) {
        switch (themeNumber) {
            case (0):
                defaultThemeRadioButton.setChecked(true);
                return;
            case (1):
                dayThemeRadioButton.setChecked(true);
                return;
            case (2):
                nightThemeRadioButton.setChecked(true);
                return;
            case (3):
                myThemeRadioButton.setChecked(true);
                return;
            default:
        }
    }

    private int getAppTheme(int themeNumber) {
        return codeStyleToStyleId(getCodeStyle(themeNumber));
    }

    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharedPref.getInt(APP_THEME, codeStyle);
    }

    void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(APP_THEME, codeStyle);
        editor.apply();
        recreate();
    }

    private int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case (0):
                return R.style.Theme_Android_1_02;
            case (1):
                return R.style.DayCalculatorTheme;
            case (2):
                return R.style.NightCalculatorTheme;
            case (3):
                return R.style.MyCalculatorTheme;
            default:
                return R.style.Theme_Android_1_02;
        }
    }
}
