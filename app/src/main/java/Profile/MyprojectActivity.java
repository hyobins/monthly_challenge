package Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

import Project.ProjectListAdapter;
import Project.ProjectListItem;

public class MyprojectActivity extends AppCompatActivity {
    ActivityMyprojectBinding binding;

    String stateTab = "진행중";

    int i=0;

//    String[] TempList = new String[100];

    String abcd;

    ArrayList<ProjectListItem> progressListItems;
    ArrayList<ProjectListItem> judgeListItems;
    ArrayList<ProjectListItem> endListItems;

    ProjectListAdapter progressProjectListAdapter;
    ProjectListAdapter judgeProjectListAdapter;
    ProjectListAdapter endProjectListAdapter;

    ProjectListItem progressListItem;
    ProjectListItem judgeListItem;
    ProjectListItem endListItem;
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_myproject);
        binding.setActivity(this);

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
                            for(QueryDocumentSnapshot document:task.getResult()){
                                Log.d("프로젝트", document.getId());
                                abcd = document.getId();
                            }
                        } else{
                            Log.d("에러", "Error getting documents: ", task.getException());
                        }
                    }
                });


        progressProjectListAdapter = new ProjectListAdapter(this, progressListItems);
        judgeProjectListAdapter = new ProjectListAdapter(this, judgeListItems);
        endProjectListAdapter = new ProjectListAdapter(this, endListItems);

    }

    public void BackButton(View v){
        finish();
    }



}