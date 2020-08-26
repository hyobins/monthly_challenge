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
    @BindView(R.id.linearLayout_serviceCat) LinearLayout linearLayout_serviceCat;
    @BindView(R.id.linearLayout_trendCat) LinearLayout linearLayout_trendCat;

    String stateTab = "진행중";

    @BindView(R.id.textView_progress) TextView textView_progress;
    @BindView(R.id.textView_judge) TextView textView_judge;
    @BindView(R.id.textView_end) TextView textView_end;

    @BindView(R.id.view_progress) View view_progress;
    @BindView(R.id.view_judge) View view_judge;
    @BindView(R.id.view_end) View view_end;

    @BindView(R.id.listView_projectList)
    ListView listview_projectList;

    @BindView(R.id.linearLayout_portfolioDetail) LinearLayout linearLayout_portfolioDetail;
    @BindView(R.id.linearLayout_trendDetail) LinearLayout linearLayout_trendDetail;
    @BindView(R.id.linearLayout_serviceDetail) LinearLayout linearLayout_serviceDetail;


    ArrayList<ProjectListItem> progressListItems;
    ArrayList<ProjectListItem> judgeListItems;
    ArrayList<ProjectListItem> endListItems;

    ProjectListAdapter progressProjectListAdapter;
    ProjectListAdapter judgeProjectListAdapter;
    ProjectListAdapter endProjectListAdapter;

    boolean portfolio = false;
    boolean trend = false;
    boolean service = false;

    TextView prevText ;
    View prevView;


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

        prevText = textView_progress;
        prevView = view_progress;

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
                        intent.putExtra("company",progressProjectListAdapter.getItem(position).getCompany());
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
                        intent.putExtra("company",judgeProjectListAdapter.getItem(position).getCompany());
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
                        intent.putExtra("company",endProjectListAdapter.getItem(position).getCompany());
                        break;

                }
                startActivity(intent);


            }
        });

        textView_progress.setOnClickListener(this);
        textView_judge.setOnClickListener(this);
        textView_end.setOnClickListener(this);
        linearLayout_projectCat.setOnClickListener(this);
        linearLayout_portfolioCat.setOnClickListener(this);
        linearLayout_serviceCat.setOnClickListener(this);
        linearLayout_trendCat.setOnClickListener(this);


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
                prevSettingAndChange(textView_progress,view_progress);
                stateTab = "진행중";
                break;
            case R.id.textView_judge:
                listview_projectList.setAdapter(judgeProjectListAdapter);
                prevSettingAndChange(textView_judge,view_judge);
                stateTab = "심사중";
                break;
            case R.id.textView_end:
                listview_projectList.setAdapter(endProjectListAdapter);
                prevSettingAndChange(textView_end,view_end);
                stateTab = "종료";
                break;
            case R.id.linearLayout_portfolioCat:
                if(!portfolio){
                    linearLayout_portfolioDetail.setVisibility(View.VISIBLE);
                    portfolio = true;
                }
                else {
                    linearLayout_portfolioDetail.setVisibility(View.GONE);
                    portfolio = false;
                }
                break;
            case R.id.linearLayout_trendCat:
                if(!trend){
                    linearLayout_trendDetail.setVisibility(View.VISIBLE);
                    trend = true;
                }
                else {
                    linearLayout_trendDetail.setVisibility(View.GONE);
                    trend = false;
                }
                break;

            case R.id.linearLayout_serviceCat:
                if(!service){
                    linearLayout_serviceDetail.setVisibility(View.VISIBLE);
                    service = true;
                }
                else {
                    linearLayout_serviceDetail.setVisibility(View.GONE);
                    service = false;
                }
                break;



        }
    }

    private void prevSettingAndChange(TextView textView, View view){
        prevText.setTextColor(Color.parseColor("#ffffff"));
        prevView.setVisibility(View.INVISIBLE);

        textView.setTextColor(Color.parseColor("#000000"));
        view.setVisibility(View.VISIBLE);

        prevText = textView;
        prevView = view;

    }




}
