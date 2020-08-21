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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MenuFragment extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    ScrollView projectInfoView;
    ScrollView projectIngView;
    LinearLayout card0;
    LinearLayout infoLayout;
    LinearLayout submitLayout;
    LinearLayout[] card;
    View infoView;
    View submitView;
    TextView infoText;
    TextView submitText;

    LinearLayout prevLayout;
    View prevView;
    TextView prevText;

    ImageView backButton;

    TextView title0;
    TextView titleText;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu, container, false);
        projectInfoView = viewGroup.findViewById(R.id.projectInfoView);
        projectIngView = viewGroup.findViewById(R.id.projectIngView);
        card0 = viewGroup.findViewById(R.id.card0);
//        card = new LinearLayout[]{card1};

        infoLayout = viewGroup.findViewById(R.id.infoLayout);
        infoView = viewGroup.findViewById(R.id.infoView);
        infoText = viewGroup.findViewById(R.id.infoText);
        submitLayout = viewGroup.findViewById(R.id.submitLayout);
        submitView = viewGroup.findViewById(R.id.submitView);
        submitText = viewGroup.findViewById(R.id.submitText);

        backButton = viewGroup.findViewById(R.id.backButton);

        title0 = viewGroup.findViewById(R.id.textView21);
        titleText = viewGroup.findViewById(R.id.titleText);

        prevLayout = infoLayout;
        prevView = infoView;
        prevText = infoText;

        card0.setOnClickListener(this);
        infoText.setOnClickListener(this);
        submitText.setOnClickListener(this);
        backButton.setOnClickListener(this);

        db.collection("project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                title0.setText(document.get("title").toString());
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

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
            case R.id.card0:
                projectIngView.setVisibility(View.GONE);
                projectInfoView.setVisibility(View.VISIBLE);
                titleText.setText(title0.getText().toString());
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
