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
import com.google.firebase.auth.FirebaseAuth;

import Login.LoginActivity;
import Profile.MyprojectActivity;
import Profile.ProfileActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingFragment extends Fragment implements View.OnClickListener{
    ViewGroup viewGroup;
    @BindView(R.id.profile) Button profile;
    @BindView(R.id.my_project) Button my_project;
    @BindView(R.id.my_point) Button my_point;
    @BindView(R.id.logout) Button logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, viewGroup);

        profile.setOnClickListener(this);
        my_project.setOnClickListener(this);
        my_point.setOnClickListener(this);
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
            case R.id.my_project:
                Intent myproject_intent = new Intent(getActivity(), MyprojectActivity.class);
                startActivity(myproject_intent);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent logout_intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(logout_intent);
                break;
        }
    }
}
