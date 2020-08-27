package Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
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

import Project.ProjectDetailActivity;
import Project.ProjectListAdapter;
import Project.ProjectListItem;

public class MyprojectActivity extends AppCompatActivity implements View.OnClickListener{
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

        prevText = binding.textViewProgress;
        prevView = binding.viewProgress;

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
                                MyProjectIDLists.add(document.getId());
                            }

                            progressListItems = MainActivity.getProgressListItem();
                            for(int i=0;i<progressListItems.size();i++){
                                String temp = progressListItems.get(i).getProjectId();
                                if(MyProjectIDLists.contains(temp)){
                                    progressListItems2.add(progressListItems.get(i));
                                }
                            }
                            judgeListItems = MainActivity.getJudgeListItem();
                            for(int i=0;i<judgeListItems.size();i++){
                                String temp = judgeListItems.get(i).getProjectId();
                                if(MyProjectIDLists.contains(temp)){
                                    judgeListItems2.add(judgeListItems.get(i));
                                }
                            }
                            endListItems = MainActivity.getEndListItem();
                            for(int i=0;i<endListItems.size();i++){
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


        binding.listViewProjectList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(context, MyprojectDetailActivity.class);
                switch (stateTab){
                    case "진행중" :
                        intent.putExtra("projectId",progressProjectListAdapter.getItem(position).getProjectId());
                        intent.putExtra("title",progressProjectListAdapter.getItem(position).getTitle());
                        intent.putExtra("deadline",progressProjectListAdapter.getItem(position).getDeadline());
                        intent.putExtra("reward",progressProjectListAdapter.getItem(position).getReward());
                        break;
                    case "심사중" :
                        intent.putExtra("projectId",judgeProjectListAdapter.getItem(position).getProjectId());
                        intent.putExtra("title",judgeProjectListAdapter.getItem(position).getTitle());
                        intent.putExtra("deadline",judgeProjectListAdapter.getItem(position).getDeadline());
                        intent.putExtra("reward",judgeProjectListAdapter.getItem(position).getReward());
                        break;
                    case "종료" :
                        intent.putExtra("projectId",endProjectListAdapter.getItem(position).getProjectId());
                        intent.putExtra("title",endProjectListAdapter.getItem(position).getTitle());
                        intent.putExtra("deadline",endProjectListAdapter.getItem(position).getDeadline());
                        intent.putExtra("reward",endProjectListAdapter.getItem(position).getReward());
                        break;

                }
                startActivity(intent);
            }
        });
        binding.textViewProgress.setOnClickListener(this);
        binding.textViewJudge.setOnClickListener(this);
        binding.textViewEnd.setOnClickListener(this);

    }

    public void BackButton(View v){
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_progress:
                binding.listViewProjectList.setAdapter(progressProjectListAdapter);
                prevSettingAndChange(binding.textViewProgress, binding.viewProgress);
                stateTab = "진행중";
                break;
            case R.id.textView_judge:
                binding.listViewProjectList.setAdapter(judgeProjectListAdapter);
                prevSettingAndChange(binding.textViewJudge, binding.viewJudge);
                stateTab = "심사중";
                break;
            case R.id.textView_end:
                binding.listViewProjectList.setAdapter(endProjectListAdapter);
                prevSettingAndChange(binding.textViewEnd, binding.viewEnd);
                stateTab = "종료";
                break;
            }
        }


    private void prevSettingAndChange(TextView textView, View view){
        prevText.setTextColor(Color.parseColor("#D6D6D6"));
        prevView.setVisibility(View.INVISIBLE);

        textView.setTextColor(Color.parseColor("#ffffff"));
        view.setVisibility(View.VISIBLE);

        prevText = textView;
        prevView = view;

    }
}



