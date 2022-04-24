package com.example.android_1_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    boolean calendarVisibility = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = findViewById(R.id.MyButton);
        ToggleButton myTB = findViewById(R.id.MyToggleButton);
        CalendarView myCalendar = findViewById(R.id.MyCalendar);
        myCalendar.setVisibility(View.INVISIBLE);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.second_layout);
            }
        });


        myTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarVisibility = !calendarVisibility;
                if (!calendarVisibility) {
                    myCalendar.setVisibility(View.INVISIBLE);
                } else {
                    myCalendar.setVisibility(View.VISIBLE);
                }

            }
        });

    }

}