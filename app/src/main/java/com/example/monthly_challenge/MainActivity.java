package com.example.monthly_challenge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.monthly_challenge.BottomNavigationFragment.*;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.monthly_challenge.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Project.ProjectListItem;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    Context context = this;
    String testId = "";
    private HomeFragment homeFragment;
    private MenuFragment menuFragment;
    private AlarmFragment alarmFragment;
    private SettingFragment settingFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private MenuItem projectIng;
    private View header;
    static ArrayList<ProjectListItem> progressListItems;
    static ArrayList<ProjectListItem> judgeListItems;
    static ArrayList<ProjectListItem> endListItems;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ProjectListItem progressListItem;
    ProjectListItem judgeListItem;
    ProjectListItem endListItem;

    SimpleDateFormat simpleDateFormat;
//    Map<String, Object> project = new HashMap<>();
//    Map<String, Object> team = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
        homeFragment = new HomeFragment();
        menuFragment = new MenuFragment();
        alarmFragment = new AlarmFragment();
        settingFragment = new SettingFragment();
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = binding.bottomNavigation;
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout, homeFragment).commitAllowingStateLoss();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        progressListItems = new ArrayList();
        judgeListItems = new ArrayList();
        endListItems = new ArrayList();
        simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

//        team.put("team_name","testteam");
//        team.put("max_developers",null);
//        team.put("max_designers",null);
//        team.put("apply_developers",null);
//        team.put("apply_designers",null);
//        team.put("openchat",null);
//
//        project.put("state",null);
//        project.put("title",null);
//        project.put("description",null);
//        project.put("deadline",null);
//        project.put("reward",null);
//        project.put("direction",null);
//        project.put("reference",null);
//        project.put("design",null);
//        project.put("team",db.collection("team").add(team));


//        db.collection("project").document().collection("team")
//                .add(team)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(null, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(null, "Error adding document", e);
//                    }
//                });


        db.collection("project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                switch (document.get("state").toString()){
                                    case "진행중":
                                        progressListItem = new ProjectListItem(document.getId(),document.get("title").toString(),document.get("company").toString(), simpleDateFormat.format(document.getTimestamp("deadline").toDate())
                                                ,document.get("reward").toString());
                                        progressListItems.add(progressListItem);

                                        break;
                                    case "심사중":
                                        judgeListItem = new ProjectListItem(document.getId(), document.get("title").toString(),document.get("company").toString(), simpleDateFormat.format(document.getTimestamp("deadline").toDate())
                                                ,document.get("reward").toString());
                                        judgeListItems.add(judgeListItem);
                                        break;
                                    case "종료":
                                        endListItem = new ProjectListItem(document.getId(), document.get("title").toString(),document.get("company").toString(), simpleDateFormat.format(document.getTimestamp("deadline").toDate())
                                                ,document.get("reward").toString());
                                        endListItems.add(endListItem);
                                        break;


                                }

//                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        System.out.println(progressListItems);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        transaction = fragmentManager.beginTransaction();
        switch (item.getItemId()){
            case R.id.navigation_home:
                transaction.replace(R.id.main_layout, homeFragment).commitAllowingStateLoss();
                return true;
            case R.id.navigation_menu:
                transaction.replace(R.id.main_layout,menuFragment).commitAllowingStateLoss();
                return true;
            case R.id.navigation_alarm:
                transaction.replace(R.id.main_layout, alarmFragment).commitAllowingStateLoss();
                return true;
            case R.id.navigation_setting:
                transaction.replace(R.id.main_layout, settingFragment).commitAllowingStateLoss();
                return true;
        }
        return false;
    }

    static public ArrayList<ProjectListItem> getProgressListItem(){
        return progressListItems;
    }
    static public ArrayList<ProjectListItem> getJudgeListItem(){
        return judgeListItems;
    }
    static public ArrayList<ProjectListItem> getEndListItem(){
        return endListItems;
    }
}