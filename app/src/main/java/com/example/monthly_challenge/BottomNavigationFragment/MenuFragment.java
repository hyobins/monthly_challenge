package com.example.monthly_challenge.BottomNavigationFragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.monthly_challenge.MainActivity;
import com.example.monthly_challenge.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import Project.ProjectListAdapter;
import Project.ProjectListItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuFragment extends Fragment implements View.OnClickListener {
    Context context;
    ViewGroup viewGroup;
    @BindView(R.id.categoryView) LinearLayout categoryView;
    @BindView(R.id.projectInfoView) ScrollView projectInfoView;
    @BindView(R.id.projectIngView) ScrollView projectIngView;
    @BindView(R.id.linearLayout_projectCat) LinearLayout linearLayout_projectCat;
    @BindView(R.id.linearLayout_portfolioCat) LinearLayout linearLayout_portfolioCat;
    @BindView(R.id.infoLayout) LinearLayout infoLayout;
    @BindView(R.id.submitLayout) LinearLayout submitLayout;
    @BindView(R.id.matchLayout) LinearLayout matchLayout;

    @BindView(R.id.infoView) View infoView;
    @BindView(R.id.matchView) View matchView;
    @BindView(R.id.submitView) View submitView;
    @BindView(R.id.infoText) TextView infoText;
    @BindView(R.id.matchText) TextView matchText;
    @BindView(R.id.submitText) TextView submitText;

    LinearLayout prevLayout;
    View prevView;
    TextView prevText;

    String stateTab = "진행중";

    @BindView(R.id.backButton) ImageView backButton;

    @BindView(R.id.titleText) TextView titleText;
    @BindView(R.id.deadlineText) TextView deadlineText;
    @BindView(R.id.rewardText) TextView rewardText;

    @BindView(R.id.textView_progress) TextView textView_progress;
    @BindView(R.id.textView_judge) TextView textView_judge;
    @BindView(R.id.textView_end) TextView textView_end;

    @BindView(R.id.listView_projectList)
    ListView listview_projectList;


    ArrayList<ProjectListItem> progressListItems;
    ArrayList<ProjectListItem> judgeListItems;
    ArrayList<ProjectListItem> endListItems;

    ProjectListAdapter progressProjectListAdapter;
    ProjectListAdapter judgeProjectListAdapter;
    ProjectListAdapter endProjectListAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

//    TeamListAdapter teamListAdapter = new TeamListAdapter(this, )


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu, container, false);
        context = container.getContext();
        ButterKnife.bind(this, viewGroup);

        prevLayout = infoLayout;
        prevView = infoView;
        prevText = infoText;

        progressListItems = MainActivity.getProgressListItem();
        judgeListItems = MainActivity.getJudgeListItem();
        endListItems = MainActivity.getEndListItem();
//        projectShowListItems = new ArrayList<ProjectListItem>();
//        judgeShowListItems = new ArrayList<JudgeListItem>();
//        endShowListItems = new ArrayList<EndListItem>();


        progressProjectListAdapter = new ProjectListAdapter(context, progressListItems);
        judgeProjectListAdapter = new ProjectListAdapter(context, judgeListItems);
        endProjectListAdapter = new ProjectListAdapter(context, endListItems);
        listview_projectList.setAdapter(progressProjectListAdapter);

        listview_projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                projectIngView.setVisibility(View.GONE);
                projectInfoView.setVisibility(View.VISIBLE);
                switch (stateTab){
                    case "진행중" :
                        titleText.setText(progressProjectListAdapter.getItem(position).getTitle());
                        deadlineText.setText(progressProjectListAdapter.getItem(position).getDeadline());
                        rewardText.setText(progressProjectListAdapter.getItem(position).getReward());
                        break;
                    case "심사중" :
                        titleText.setText(judgeProjectListAdapter.getItem(position).getTitle());
                        deadlineText.setText(judgeProjectListAdapter.getItem(position).getDeadline());
                        rewardText.setText(judgeProjectListAdapter.getItem(position).getReward());
                        break;
                    case "종료" :
                        titleText.setText(endProjectListAdapter.getItem(position).getTitle());
                        deadlineText.setText(endProjectListAdapter.getItem(position).getDeadline());
                        rewardText.setText(endProjectListAdapter.getItem(position).getReward());
                        break;

                }


            }
        });

        linearLayout_projectCat.setOnClickListener(this);
        infoText.setOnClickListener(this);
        matchText.setOnClickListener(this);
        submitText.setOnClickListener(this);
        backButton.setOnClickListener(this);
        textView_progress.setOnClickListener(this);
        textView_judge.setOnClickListener(this);
        textView_end.setOnClickListener(this);


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
            case R.id.linearLayout_projectCat:
                categoryView.setVisibility(View.GONE);
                projectIngView.setVisibility(View.VISIBLE);
                break;

            case R.id.textView_progress:
                listview_projectList.setAdapter(progressProjectListAdapter);
                stateTab = "진행중";
                break;
            case R.id.textView_judge:
                listview_projectList.setAdapter(judgeProjectListAdapter);
                stateTab = "심사중";
                break;
            case R.id.textView_end:
                listview_projectList.setAdapter(endProjectListAdapter);
                stateTab = "종료";
                break;

            case R.id.submitText:

                prevSettingAndChange(submitText,submitView,submitLayout);

                break;
            case R.id.matchText:

                prevSettingAndChange(matchText,matchView,matchLayout);

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
