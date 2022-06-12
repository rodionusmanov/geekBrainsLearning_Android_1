package com.example.android_1_03;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class DateDialogFragment extends DialogFragment {
    public static final String TAG = "DatePickerDialog";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View customView = inflater.inflate(R.layout.date_picker_dialog, null);
        DatePicker datePicker = customView.findViewById(R.id.date_picker_dialog);
        customView.findViewById(R.id.save_date_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder dateBuilder = new StringBuilder()
                        .append(datePicker.getDayOfMonth())
                        .append(".").append(datePicker.getMonth() + 1)
                        .append(".").append(datePicker.getYear());
                TextView tv = requireActivity().findViewById(R.id.note_date);
                tv.setText(dateBuilder.toString());
                dismiss();
            }
        });

        return customView;
    }
}

