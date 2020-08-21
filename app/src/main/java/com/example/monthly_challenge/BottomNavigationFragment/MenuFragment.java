package com.example.monthly_challenge.BottomNavigationFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.monthly_challenge.ListItem;
import com.example.monthly_challenge.MainActivity;
import com.example.monthly_challenge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuFragment extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    @BindView(R.id.projectInfoView) ScrollView projectInfoView;
    @BindView(R.id.projectIngView) ScrollView projectIngView;
    @BindView(R.id.infoLayout) LinearLayout infoLayout;
    @BindView(R.id.submitLayout) LinearLayout submitLayout;
    @BindView(R.id.linearLayout_list0) LinearLayout linearLayout_list0;
    @BindView(R.id.linearLayout_list1) LinearLayout linearLayout_list1;
    @BindView(R.id.linearLayout_list2) LinearLayout linearLayout_list2;
    @BindView(R.id.linearLayout_list3) LinearLayout linearLayout_list3;
    @BindView(R.id.linearLayout_list4) LinearLayout linearLayout_list4;
    @BindView(R.id.linearLayout_list5) LinearLayout linearLayout_list5;
    LinearLayout[] linearLayout_lists;
    @BindView(R.id.textView_listTitle0) TextView textView_listTitle0;
    @BindView(R.id.textView_listTitle1) TextView textView_listTitle1;
    @BindView(R.id.textView_listTitle2) TextView textView_listTitle2;
    @BindView(R.id.textView_listTitle3) TextView textView_listTitle3;
    @BindView(R.id.textView_listTitle4) TextView textView_listTitle4;
    @BindView(R.id.textView_listTitle5) TextView textView_listTitle5;
    TextView[] textView_listTitles;
    @BindView(R.id.textView_listDeadline0) TextView textView_listDeadline0;
    @BindView(R.id.textView_listDeadline1) TextView textView_listDeadline1;
    @BindView(R.id.textView_listDeadline2) TextView textView_listDeadline2;
    @BindView(R.id.textView_listDeadline3) TextView textView_listDeadline3;
    @BindView(R.id.textView_listDeadline4) TextView textView_listDeadline4;
    @BindView(R.id.textView_listDeadline5) TextView textView_listDeadline5;
    TextView[] textView_listDeadlines;
    @BindView(R.id.textView_listReward0) TextView textView_listReward0;
    @BindView(R.id.textView_listReward1) TextView textView_listReward1;
    @BindView(R.id.textView_listReward2) TextView textView_listReward2;
    @BindView(R.id.textView_listReward3) TextView textView_listReward3;
    @BindView(R.id.textView_listReward4) TextView textView_listReward4;
    @BindView(R.id.textView_listReward5) TextView textView_listReward5;
    TextView[] textView_listRewards;

    @BindView(R.id.infoView) View infoView;
    @BindView(R.id.submitView) View submitView;
    @BindView(R.id.infoText) TextView infoText;
    @BindView(R.id.submitText) TextView submitText;

    LinearLayout prevLayout;
    View prevView;
    TextView prevText;

    @BindView(R.id.backButton) ImageView backButton;

    @BindView(R.id.titleText) TextView titleText;
    @BindView(R.id.deadlineText) TextView deadlineText;
    @BindView(R.id.rewardText) TextView rewardText;

    ArrayList<ListItem> listItems;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, viewGroup);
        projectInfoView = viewGroup.findViewById(R.id.projectInfoView);
        projectIngView = viewGroup.findViewById(R.id.projectIngView);


        linearLayout_lists = new LinearLayout[]{linearLayout_list0,linearLayout_list1,linearLayout_list3,
                                                linearLayout_list4,linearLayout_list5};

        textView_listTitles = new TextView[]{textView_listTitle0,textView_listTitle1,textView_listTitle2,
                                            textView_listTitle3,textView_listTitle4,textView_listTitle5};
        textView_listDeadlines = new TextView[]{textView_listDeadline0,textView_listDeadline1,
                                    textView_listDeadline3,textView_listDeadline4,textView_listDeadline5};
        textView_listRewards = new TextView[]{textView_listReward0,textView_listReward1,textView_listReward2,
                                    textView_listReward3,textView_listReward4,textView_listReward5};


        prevLayout = infoLayout;
        prevView = infoView;
        prevText = infoText;

        listItems = MainActivity.getListItem();
        System.out.println(listItems);

        for(int i=0;i<linearLayout_lists.length;i++){
            linearLayout_lists[i].setOnClickListener(this);
        }
        infoText.setOnClickListener(this);
        submitText.setOnClickListener(this);
        backButton.setOnClickListener(this);

        if(listItems.size() <= linearLayout_lists.length){
            for(int i=0;i<listItems.size();i++){
                textView_listTitles[i].setText(listItems.get(i).getTitle());
                textView_listDeadlines[i].setText(listItems.get(i).getDeadline());
                textView_listRewards[i].setText(listItems.get(i).getReward());
            }
        }
        else{
            for(int i=0;i<linearLayout_lists.length;i++){
                textView_listTitles[i].setText(listItems.get(i).getTitle());
                textView_listDeadlines[i].setText(listItems.get(i).getDeadline());
                textView_listRewards[i].setText(listItems.get(i).getReward());
            }
        }

//        db.collection("project")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                ListItem listItem = new ListItem(document.get("title").toString(),document.get("deadline").toString(),document.get("reward").toString());
////                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
////                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });

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
        TextView textView;
        switch (v.getId()){
            case R.id.linearLayout_list0:
                textView = textView_listTitle0;
                if(textView.getText().equals("")) break;
                onClickListItem(0);
                break;
            case R.id.linearLayout_list1:
                textView = textView_listTitle1;
                if(textView.getText().equals("")) break;
                onClickListItem(1);
                break;
            case R.id.linearLayout_list2:
                textView = textView_listTitle2;
                if(textView.getText().equals("")) break;
                onClickListItem(2);
                break;
            case R.id.linearLayout_list3:
                textView = textView_listTitle3;
                if(textView.getText().equals("")) break;
                onClickListItem(3);
                break;
            case R.id.linearLayout_list4:
                textView = textView_listTitle4;
                if(textView.getText().equals("")) break;
                onClickListItem(4);
                break;
            case R.id.linearLayout_list5:
                textView = textView_listTitle5;
                if(textView.getText().equals("")) break;
                onClickListItem(5);
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
    private void onClickListItem(int i){
        projectIngView.setVisibility(View.GONE);
        projectInfoView.setVisibility(View.VISIBLE);
        titleText.setText(textView_listTitles[i].getText().toString());
        deadlineText.setText(textView_listDeadlines[i].getText().toString());
        rewardText.setText(textView_listRewards[i].getText().toString());
    }

}
