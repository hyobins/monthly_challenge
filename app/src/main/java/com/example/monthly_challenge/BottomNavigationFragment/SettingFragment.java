package com.example.monthly_challenge.BottomNavigationFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.monthly_challenge.R;

import Login.IndivSignupActivity;
import Profile.ProfileActivity;


public class SettingFragment extends Fragment implements View.OnClickListener{
    ViewGroup viewGroup;
    Button profile, my_project, team_building, logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);

        profile = viewGroup.findViewById(R.id.profile);
        profile.setOnClickListener(this);
        my_project = viewGroup.findViewById(R.id.my_project);
        my_project.setOnClickListener(this);
        team_building = viewGroup.findViewById(R.id.team_building);
        team_building.setOnClickListener(this);
        logout = viewGroup.findViewById(R.id.logout);
        logout.setOnClickListener(this);

        return viewGroup;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.profile:
                Intent intent = new Intent(getActivity() , ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }
}
