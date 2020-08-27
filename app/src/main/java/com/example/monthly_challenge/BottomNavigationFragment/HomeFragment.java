package com.example.monthly_challenge.BottomNavigationFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.monthly_challenge.MainActivity;
import com.example.monthly_challenge.R;

import java.util.ArrayList;

import Project.HomeProjectAdapter;
import Project.ProjectListAdapter;
import Project.ProjectListItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    Context context;

    static ArrayList<ProjectListItem> progressListItems = new ArrayList<>();;

    @BindView(R.id.HomeProjectListView)
    ListView HomeProjectListView;
    @BindView(R.id.textView_listCompany)
    TextView textView_listComapny0;
    @BindView(R.id.textView_listTitle)
    TextView textView_listTitle0;
    @BindView(R.id.textView_listReward)
    TextView textView_listReward0;
    @BindView(R.id.textView_listDeadline)
    TextView textView_listDedline0;

    @BindView(R.id.textView_listCompany1)
    TextView textView_listComapny1;
    @BindView(R.id.textView_listTitle1)
    TextView textView_listTitle1;
    @BindView(R.id.textView_listReward1)
    TextView textView_listReward1;
    @BindView(R.id.textView_listDeadline1)
    TextView textView_listDedline1;

    @BindView(R.id.textView_listCompany2)
    TextView textView_listComapny2;
    @BindView(R.id.textView_listTitle2)
    TextView textView_listTitle2;
    @BindView(R.id.textView_listReward2)
    TextView textView_listReward2;
    @BindView(R.id.textView_listDeadline2)
    TextView textView_listDedline2;

    @BindView(R.id.textView_listCompany3)
    TextView textView_listComapny3;
    @BindView(R.id.textView_listTitle3)
    TextView textView_listTitle3;
    @BindView(R.id.textView_listReward3)
    TextView textView_listReward3;
    @BindView(R.id.textView_listDeadline3)
    TextView textView_listDedline3;

    @BindView(R.id.textView_listCompany4)
    TextView textView_listComapny4;
    @BindView(R.id.textView_listTitle4)
    TextView textView_listTitle4;
    @BindView(R.id.textView_listReward4)
    TextView textView_listReward4;
    @BindView(R.id.textView_listDeadline4)
    TextView textView_listDedline4;

    @BindView(R.id.more_project)
    Button more_project;


    HomeProjectAdapter homeProjectAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        context = container.getContext();
        ButterKnife.bind(this, viewGroup);

        progressListItems = MainActivity.getProgressListItem();
        for(int i=0;i<progressListItems.size();i++){
            System.out.println("진행중인 프로젝트"+ progressListItems.get(i).getProjectId());
            System.out.println("이건?");
        }
        textView_listTitle0.setText(progressListItems.get(0).getTitle());
        textView_listComapny0.setText(progressListItems.get(0).getCompany());
        textView_listDedline0.setText(progressListItems.get(0).getDeadline());
        textView_listReward0.setText(progressListItems.get(0).getReward());

        textView_listTitle1.setText(progressListItems.get(1).getTitle());
        textView_listComapny1.setText(progressListItems.get(1).getCompany());
        textView_listDedline1.setText(progressListItems.get(1).getDeadline());
        textView_listReward1.setText(progressListItems.get(1).getReward());

        textView_listTitle2.setText(progressListItems.get(2).getTitle());
        textView_listComapny2.setText(progressListItems.get(2).getCompany());
        textView_listDedline2.setText(progressListItems.get(2).getDeadline());
        textView_listReward2.setText(progressListItems.get(2).getReward());

        textView_listTitle3.setText(progressListItems.get(3).getTitle());
        textView_listComapny3.setText(progressListItems.get(3).getCompany());
        textView_listDedline3.setText(progressListItems.get(3).getDeadline());
        textView_listReward3.setText(progressListItems.get(3).getReward());

        textView_listTitle4.setText(progressListItems.get(4).getTitle());
        textView_listComapny4.setText(progressListItems.get(4).getCompany());
        textView_listDedline4.setText(progressListItems.get(4).getDeadline());
        textView_listReward4.setText(progressListItems.get(4).getReward());


//        homeProjectAdapter = new HomeProjectAdapter(context, progressListItems);
//        HomeProjectListView.setAdapter(homeProjectAdapter);



        return viewGroup;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_project:

                break;
        }
    }
}
