package com.example.monthly_challenge.BottomNavigationFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityMainBinding;
import com.example.monthly_challenge.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    ScrollView projectInfoView;
    ScrollView projectIngView;
    LinearLayout card1;
    LinearLayout infoLayout;
    LinearLayout submitLayout;
    View infoView;
    View submitView;
    TextView infoText;
    TextView submitText;

    LinearLayout prevLayout;
    View prevView;
    TextView prevText;

    ImageView backButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu, container, false);
        projectInfoView = viewGroup.findViewById(R.id.projectInfoView);
        projectIngView = viewGroup.findViewById(R.id.projectIngView);
        card1 = viewGroup.findViewById(R.id.card1);
        infoLayout = viewGroup.findViewById(R.id.infoLayout);
        infoView = viewGroup.findViewById(R.id.infoView);
        infoText = viewGroup.findViewById(R.id.infoText);
        submitLayout = viewGroup.findViewById(R.id.submitLayout);
        submitView = viewGroup.findViewById(R.id.submitView);
        submitText = viewGroup.findViewById(R.id.submitText);

        backButton = viewGroup.findViewById(R.id.backButton);

        prevLayout = infoLayout;
        prevView = infoView;
        prevText = infoText;

        card1.setOnClickListener(this);
        infoText.setOnClickListener(this);
        submitText.setOnClickListener(this);
        backButton.setOnClickListener(this);


        return viewGroup;
    }
    private void prevSettingAndChange(TextView textView, View view, LinearLayout linearLayout){
        prevText.setTextColor(Color.parseColor("#808080"));
        prevView.setVisibility(View.INVISIBLE);
        prevLayout.setVisibility(View.GONE);

        textView.setTextColor(Color.parseColor("#000000"));
        view.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);

        prevText = textView;
        prevView = view;
        prevLayout = linearLayout;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card1:
                projectIngView.setVisibility(View.GONE);
                projectInfoView.setVisibility(View.VISIBLE);
                break;
            case R.id.submitText:

                prevSettingAndChange(submitText,submitView,submitLayout);

                break;
            case R.id.infoText:

                prevSettingAndChange(infoText,infoView, infoLayout);

                break;

            case R.id.backButton:
                projectIngView.setVisibility(View.VISIBLE);
                projectInfoView.setVisibility(View.GONE);
                break;

        }
    }
}
