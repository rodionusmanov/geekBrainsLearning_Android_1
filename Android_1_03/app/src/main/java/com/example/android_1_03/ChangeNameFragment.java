package com.example.android_1_03;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChangeNameFragment extends Fragment {
    TextView nameTV;
    EditText nameEV;
    Button applyChangesButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.change_name_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        Intent nameStr = new Intent(requireActivity(), MainActivity.class);
        nameTV = view.findViewById(R.id.textViewOnChangeName);
        nameEV = view.findViewById(R.id.editTextOnChangeName);
        nameEV.setText(bundle.getString("UserName"));
        applyChangesButton = view.findViewById(R.id.applyChangesButton);

        applyChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameStr.putExtra("AvatarId", bundle.getInt("AvatarId"));
                nameStr.putExtra("StartScreenWorked", true);
                nameStr.putExtra("NameStr", String.valueOf(nameEV.getText()));
                requireActivity().finish();
                startActivity(nameStr);
            }
        });
    }
}
