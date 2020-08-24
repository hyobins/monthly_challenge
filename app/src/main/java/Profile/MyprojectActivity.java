package Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monthly_challenge.MainActivity;
import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityMyprojectBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import Project.ProjectListAdapter;
import Project.ProjectListItem;

public class MyprojectActivity extends AppCompatActivity {
    ActivityMyprojectBinding binding;

    Context context = this;

    String stateTab = "진행중";
    LinearLayout prevLayout;
    View prevView;
    TextView prevText;

    //my project id 임시저장
    ArrayList<String> MyProjectIDLists = new ArrayList<String>();

    ArrayList<ProjectListItem> progressListItems;
    ArrayList<ProjectListItem> judgeListItems;
    ArrayList<ProjectListItem> endListItems;

    ArrayList<ProjectListItem> progressListItems2 = new ArrayList<ProjectListItem>();
    ArrayList<ProjectListItem> judgeListItems2 = new ArrayList<ProjectListItem>();
    ArrayList<ProjectListItem> endListItems2 = new ArrayList<ProjectListItem>();

    ProjectListAdapter progressProjectListAdapter;
    ProjectListAdapter judgeProjectListAdapter;
    ProjectListAdapter endProjectListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_myproject);
        binding.setActivity(this);

        prevLayout = binding.infoLayout;
        prevView = binding.infoView;
        prevText = binding.infoText;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query projectIDQuery = db
                .collection("individual")
                .document(user.getUid())
                .collection("my_project");

        projectIDQuery
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())){
                                MyProjectIDLists.add(document.getId().toString());
                            }

                            progressListItems = MainActivity.getProgressListItem();
                            for(int i=0;i<progressListItems.size();i++){
//                                System.out.println("진행 출력성공"+progressListItems.get(i).getProjectId());
                                String temp = progressListItems.get(i).getProjectId();
                                if(MyProjectIDLists.contains(temp)){
                                    progressListItems2.add(progressListItems.get(i));
                                }
                            }
                            judgeListItems = MainActivity.getJudgeListItem();
                            for(int i=0;i<judgeListItems.size();i++){
//                                System.out.println("심사 출력성공"+judgeListItems.get(i).getProjectId());
                                String temp = judgeListItems.get(i).getProjectId();
                                if(MyProjectIDLists.contains(temp)){
                                    judgeListItems2.add(judgeListItems.get(i));
                                }
                            }

                            endListItems = MainActivity.getEndListItem();
                            for(int i=0;i<endListItems.size();i++){
//                                System.out.println("출력성공"+endListItems.get(i).getProjectId());
                                String temp = endListItems.get(i).getProjectId();
                                if(MyProjectIDLists.contains(temp)){
                                    endListItems2.add(endListItems.get(i));
                                }
                            }

                            progressProjectListAdapter = new ProjectListAdapter(context, progressListItems2);
                            judgeProjectListAdapter = new ProjectListAdapter(context, judgeListItems2);
                            endProjectListAdapter = new ProjectListAdapter(context, endListItems2);

                            binding.listViewProjectList.setAdapter(progressProjectListAdapter);
                        } else{
                            Log.d("에러", "Error getting documents: ", task.getException());
                        }
                    }
                });


        binding.listViewProjectList.setOnItemClickListener((parent, view, position, id) -> {
            binding.projectIngView.setVisibility(View.GONE);
            binding.projectInfoView.setVisibility(View.VISIBLE);
            switch (stateTab){
                case "진행중" :
                    binding.titleText.setText(progressProjectListAdapter.getItem(position).getTitle());
                    binding.deadlineText.setText(progressProjectListAdapter.getItem(position).getDeadline());
                    binding.rewardText.setText(progressProjectListAdapter.getItem(position).getReward());
                    break;
                case "심사중" :
                    binding.titleText.setText(judgeProjectListAdapter.getItem(position).getTitle());
                    binding.deadlineText.setText(judgeProjectListAdapter.getItem(position).getDeadline());
                    binding.rewardText.setText(judgeProjectListAdapter.getItem(position).getReward());
                    break;
                case "종료" :
                    binding.titleText.setText(endProjectListAdapter.getItem(position).getTitle());
                    binding.deadlineText.setText(endProjectListAdapter.getItem(position).getDeadline());
                    binding.rewardText.setText(endProjectListAdapter.getItem(position).getReward());
                    break;
            }
        });


    }

    public void BackButton(View v){
        finish();
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

    public void tabprogress(View v){
        binding.listViewProjectList.setAdapter(progressProjectListAdapter);
        stateTab = "진행중";
    }

    public void tabjudge(View v){
        binding.listViewProjectList.setAdapter(judgeProjectListAdapter);
        stateTab = "심사중";
    }

    public void tabend(View v){
        binding.listViewProjectList.setAdapter(endProjectListAdapter);
        stateTab = "종료";
    }




}