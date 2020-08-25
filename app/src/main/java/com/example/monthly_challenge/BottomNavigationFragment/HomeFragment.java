package com.example.monthly_challenge.BottomNavigationFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class HomeFragment extends Fragment {
    ViewGroup viewGroup;
    Context context;

    static ArrayList<ProjectListItem> progressListItems = new ArrayList<>();;

    @BindView(R.id.HomeProjectListView)
    ListView HomeProjectListView;

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


        homeProjectAdapter = new HomeProjectAdapter(context, progressListItems);
        HomeProjectListView.setAdapter(homeProjectAdapter);



        return viewGroup;
    }
}
