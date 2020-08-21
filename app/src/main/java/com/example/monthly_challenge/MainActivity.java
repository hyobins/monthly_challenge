package com.example.monthly_challenge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    Context context = this;
    private HomeFragment homeFragment;
    private MenuFragment menuFragment;
    private AlarmFragment alarmFragment;
    private SettingFragment settingFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private DrawerLayout drawerLayout;
    private NavigationView drawerNavigationView;
    private MenuItem projectIng;
    private View header;
    static ArrayList<ProgressListItem> progressListItems;
    static ArrayList<JudgeListItem> judgeListItems;
    static ArrayList<EndListItem> endListItems;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ProgressListItem progressListItem;
    JudgeListItem judgeListItem;
    EndListItem endListItem;

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

        drawerLayout = binding.drawerLayout;
        drawerNavigationView = binding.navView;
        drawerNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                projectIng = drawerNavigationView.inflateMenu(R.menu.drawer_nav_menu)
                switch (item.getItemId()){
                    case R.id.project:
//                        if(projectIng.isVisible()) projectIng.setVisible(false);
//                        else projectIng.setVisible(true);
                        break;
                    case R.id.project_ing:
//                        projectIng.setVisible(true);
                        transaction.replace(R.id.main_layout, menuFragment).commitAllowingStateLoss();
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;

                }
                return false;
            }
        });
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
                                        progressListItem = new ProgressListItem(document.get("title").toString(), simpleDateFormat.format(document.getTimestamp("deadline").toDate())
                                                ,document.get("reward").toString());
                                        progressListItems.add(progressListItem);

                                        break;
                                    case "심사중":
                                        judgeListItem = new JudgeListItem(document.get("title").toString(), simpleDateFormat.format(document.getTimestamp("deadline").toDate())
                                                ,document.get("reward").toString());
                                        judgeListItems.add(judgeListItem);
                                        break;
                                    case "종료":
                                        endListItem = new EndListItem(document.get("title").toString(), simpleDateFormat.format(document.getTimestamp("deadline").toDate())
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
                drawerLayout.openDrawer(GravityCompat.START);
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

    static public ArrayList<ProgressListItem> getProgressListItem(){
        return progressListItems;
    }
    static public ArrayList<JudgeListItem> getJudgeListItem(){
        return judgeListItems;
    }
    static public ArrayList<EndListItem> getEndListItem(){
        return endListItems;
    }
}