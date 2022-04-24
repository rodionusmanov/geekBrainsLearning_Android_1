package com.example.android_1_01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class second_layout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        Button myButtonTwo = findViewById(R.id.MyButtonTwo);
        Switch mySwitchTwo = findViewById(R.id.MySwitchTwo);

        mySwitchTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myButtonTwo.setVisibility(View.INVISIBLE);
            }
        });

    }
}
