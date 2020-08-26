package com.example.monthly_challenge.BottomNavigationFragment;

import android.content.Context;
import android.content.Intent;
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
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.fragment.app.Fragment;

import com.example.monthly_challenge.MainActivity;
import com.example.monthly_challenge.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import Project.ProjectDetailActivity;
import Project.ProjectListAdapter;
import Project.ProjectListItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuFragment extends Fragment implements View.OnClickListener {
    Context context;
    ViewGroup viewGroup;
    @BindView(R.id.categoryView) LinearLayout categoryView;

    @BindView(R.id.projectIngView) ScrollView projectIngView;
    @BindView(R.id.linearLayout_projectCat) LinearLayout linearLayout_projectCat;
    @BindView(R.id.linearLayout_portfolioCat) LinearLayout linearLayout_portfolioCat;

    String stateTab = "진행중";

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



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu, container, false);
        context = container.getContext();
        ButterKnife.bind(this, viewGroup);


        progressListItems = MainActivity.getProgressListItem();
        judgeListItems = MainActivity.getJudgeListItem();
        endListItems = MainActivity.getEndListItem();


        progressProjectListAdapter = new ProjectListAdapter(context, progressListItems);
        judgeProjectListAdapter = new ProjectListAdapter(context, judgeListItems);
        endProjectListAdapter = new ProjectListAdapter(context, endListItems);
        listview_projectList.setAdapter(progressProjectListAdapter);

        listview_projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ProjectDetailActivity.class);
                switch (stateTab){
                    case "진행중" :
                        intent.putExtra("projectId",progressProjectListAdapter.getItem(position).getProjectId());
                        intent.putExtra("title",progressProjectListAdapter.getItem(position).getTitle());
                        intent.putExtra("deadline",progressProjectListAdapter.getItem(position).getDeadline());
                        intent.putExtra("reward",progressProjectListAdapter.getItem(position).getReward());
                        intent.putExtra("direction", progressProjectListAdapter.getItem(position).getDirection());
                        intent.putExtra("state", progressProjectListAdapter.getItem(position).getState());
                        intent.putExtra("submit", progressProjectListAdapter.getItem(position).getSubmit());
                        intent.putExtra("description", progressProjectListAdapter.getItem(position).getDescription());
                        break;
                    case "심사중" :
                        intent.putExtra("projectId",judgeProjectListAdapter.getItem(position).getProjectId());
                        intent.putExtra("title",judgeProjectListAdapter.getItem(position).getTitle());
                        intent.putExtra("deadline",judgeProjectListAdapter.getItem(position).getDeadline());
                        intent.putExtra("reward",judgeProjectListAdapter.getItem(position).getReward());
                        intent.putExtra("direction", judgeProjectListAdapter.getItem(position).getDirection());
                        intent.putExtra("state", judgeProjectListAdapter.getItem(position).getState());
                        intent.putExtra("submit", judgeProjectListAdapter.getItem(position).getSubmit());
                        intent.putExtra("description", judgeProjectListAdapter.getItem(position).getDescription());
                        break;
                    case "종료" :
                        intent.putExtra("projectId",endProjectListAdapter.getItem(position).getProjectId());
                        intent.putExtra("title",endProjectListAdapter.getItem(position).getTitle());
                        intent.putExtra("deadline",endProjectListAdapter.getItem(position).getDeadline());
                        intent.putExtra("reward",endProjectListAdapter.getItem(position).getReward());
                        intent.putExtra("direction", endProjectListAdapter.getItem(position).getDirection());
                        intent.putExtra("state", endProjectListAdapter.getItem(position).getState());
                        intent.putExtra("submit", endProjectListAdapter.getItem(position).getSubmit());
                        intent.putExtra("description", endProjectListAdapter.getItem(position).getDescription());
                        break;

                }
                startActivity(intent);


            }
        });

        linearLayout_projectCat.setOnClickListener(this);
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

        }
    }




}
