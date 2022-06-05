package com.example.android_1_03;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ChooseAvatarFragment extends Fragment {
    ImageView homerIV;
    ImageView benderIV;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setActionBar();
        return inflater.inflate(R.layout.choose_user_avatar, container, false);
    }

    private void setActionBar() {
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity)
                requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(R.string.choose_avatar);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();

        Intent avatarIdStr = new Intent(requireActivity(), MainActivity.class);
        homerIV = view.findViewById(R.id.homerAvatar);
        benderIV = view.findViewById(R.id.benderAvatar);

        homerIV.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                avatarIdStr.putExtra("AvatarId", R.drawable.homer_work);
                avatarIdStr.putExtra("StartScreenWorked", true);
                avatarIdStr.putExtra("NameStr", bundle.getString("UserName"));
                requireActivity().finish();
                startActivity(avatarIdStr);
            }
        });

        benderIV.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                avatarIdStr.putExtra("AvatarId", R.drawable.bender_avatar);
                avatarIdStr.putExtra("StartScreenWorked", true);
                avatarIdStr.putExtra("NameStr", bundle.getString("UserName"));
                requireActivity().finish();
                startActivity(avatarIdStr);
            }
        });
    }
}
