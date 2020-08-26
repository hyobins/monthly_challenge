package Project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monthly_challenge.MainActivity;
import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityProjectdetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.okhttp.internal.Internal;

import java.util.ArrayList;
import java.util.HashMap;

import Profile.IndividualItem;
import Project.Team.TeamListAdapter;
import Project.Team.TeamListItem;
import Project.Team.TeamPopupActivity;

public class ProjectDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    Intent intent;
    ActivityProjectdetailBinding binding;
    String projectId;
    LinearLayout prevLayout;
    View prevView;
    TextView prevText;

//    String teamName;
    ArrayList<String> teamNames;
    TeamListItem teamListItem;
    static ArrayList<TeamListItem> teamListItems;
    TeamListAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    IndividualItem individualItem;
    boolean projectInIndividual = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_projectdetail);
        binding.setActivity(this);

        intent = getIntent();
        projectId = intent.getStringExtra("projectId");
        binding.titleText.setText(intent.getStringExtra("title"));
        binding.deadlineText.setText(intent.getStringExtra("deadline"));
        binding.rewardText.setText(intent.getStringExtra("reward"));

        binding.backButton.setOnClickListener(this);
        binding.submitText.setOnClickListener(this);
        binding.matchText.setOnClickListener(this);
        binding.infoText.setOnClickListener(this);
        binding.buttonCreateTeam.setOnClickListener(this);
        prevLayout = binding.infoLayout;
        prevView = binding.infoView;
        prevText = binding.infoText;

        teamNames = new ArrayList<String>();
        teamListItems = new ArrayList<TeamListItem>();

        individualItem = MainActivity.getIndividualItem();
        db.collection("individual")
                .document(individualItem.getUid())
                .collection("my_project")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("일치안해??? " + document.getId() + ", " + projectId);
                                if(document.getId().equals(projectId)){
                                    projectInIndividual = true;
                                    break;
                                }
                            }
                        }
                    }
                });


        db.collection("project")
                .document(projectId)
                .collection("team")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                teamListItem = new TeamListItem(document.getId(), Integer.valueOf(document.get("max_designers").toString()),Integer.valueOf(document.get("max_developers").toString())
                                                                ,Integer.valueOf(document.get("apply_designers").toString()),Integer.valueOf(document.get("apply_developers").toString()), document.get("openchat_url").toString());
                                teamListItems.add(teamListItem);
                            }
                            adapter = new TeamListAdapter(context, teamListItems);
                            binding.listViewTeamList.setAdapter(adapter);
                        }
                        else{

                        }
                    }
                });
        binding.listViewTeamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TeamListItem selectItem = teamListItems.get(position);
                String teamName = selectItem.getTeamName();
                if(projectInIndividual){
                    Toast.makeText(getApplicationContext(),"이미 참여한 팀이 있습니다",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if((selectItem.getApply_developers() == 3 && selectItem.getApply_developers() == selectItem.getMax_developers()) && individualItem.getPosition() == "개발자"){
                    Toast.makeText(getApplicationContext(), "더 이상 지원할 수 없습니다",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if((selectItem.getApply_designers() == 3 && selectItem.getApply_designers() == selectItem.getMax_designers()) && individualItem.getPosition() == "디자이너"){
                    Toast.makeText(getApplicationContext(), "더 이상 지원할 수 없습니다",Toast.LENGTH_SHORT).show();
                    return ;
                }

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Light_Dialog);
                dialogBuilder.setMessage( "팀에 참여하시겠습니까?")
                        .setTitle(teamName)
                        .setPositiveButton("예", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                onClickPositive(teamName, position);
                                dialog.cancel();

                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                        .show();

//                db.collection("project")
//                        .document(projectId)
//                        .collection("team")
//                        .document(teamName)
//                        .collection("members")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        document.get()
//                                    }
//                                }
//                            }
//                        });
                projectInIndividual = true;

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                finish();
                break;
            case R.id.submitText:

                prevSettingAndChange(binding.submitText,binding.submitView,binding.submitLayout);

                break;
            case R.id.matchText:

                prevSettingAndChange(binding.matchText,binding.matchView,binding.matchLayout);

                break;
            case R.id.infoText:

                prevSettingAndChange(binding.infoText,binding.infoView, binding.infoLayout);

                break;
            case R.id.button_createTeam:
                Intent intent = new Intent(context, TeamPopupActivity.class);
                intent.putExtra("projectId",projectId);
                startActivityForResult(intent,1);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if(projectInIndividual){
                    Toast.makeText(getApplicationContext(),"이미 참여한 팀이 있습니다",Toast.LENGTH_SHORT).show();
                    return ;
                }
//                System.out.println(data.getStringExtra("new_teamName") + ", " + Integer.valueOf(data.getStringExtra("new_maxDesigners")) + ", " + data.getStringExtra("new_openchatUrl"));
                String new_teamName = data.getStringExtra("new_teamName");
                int new_maxDesigners = data.getExtras().getInt("new_maxDesigners");
                int new_maxDevelopers = data.getExtras().getInt("new_maxDevelopers");
                int new_applyDesigners =  data.getExtras().getInt("new_applyDesigners");
                int new_applyDevelopers = data.getExtras().getInt("new_applyDevelopers");
                String new_openchatUrl =  data.getStringExtra("new_openchatUrl");
                System.out.println(new_teamName +", " + new_maxDesigners +", " + new_maxDevelopers +", " + new_applyDesigners+", " + new_applyDevelopers+", " + new_openchatUrl);
                if(new_applyDesigners == 0)
                    new_applyDevelopers = 1;
                else new_applyDevelopers = 0;
                teamListItem = new TeamListItem(new_teamName, new_maxDesigners, new_maxDevelopers, new_applyDesigners, new_applyDevelopers, new_openchatUrl);
                System.out.println("apply Developers2 : " + TeamPopupActivity.getApplyDevelopers());
//                teamListItem = (TeamListItem) data.getSerializableExtra("newTeamListItem");
                teamListItems.add(teamListItem);
                adapter.notifyDataSetChanged();

                HashMap<String, Object> newMyProjectField = new HashMap<>();
                newMyProjectField.put("team_name",data.getStringExtra("new_teamName"));
                db.collection("individual")
                        .document(individualItem.getUid())
                        .collection("my_project")
                        .document(projectId)
                        .set(newMyProjectField)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                projectInIndividual = true;
            }
        }
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

    public static ArrayList<TeamListItem> getTeamListItems(){
        return teamListItems;
    }

    private void onClickPositive(String teamName, int position){
        if(individualItem.getPosition().equals("개발자")){
            HashMap<String, Object> newField = new HashMap<>();
            newField.put("position", "개발자");
            db.collection("project")
                    .document(projectId)
                    .collection("team")
                    .document(teamName)
                    .collection("members")
                    .document(individualItem.getUid())
                    .set(newField)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            int apply_developers = teamListItems.get(position).getApply_developers()+1;
                            db.collection("project")
                                    .document(projectId)
                                    .collection("team")
                                    .document(teamName)
                                    .update("apply_developers",apply_developers);
                            teamListItems.get(position).setApply_developers(apply_developers);
                            adapter.notifyDataSetChanged();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
        else{
            HashMap<String, Object> newField = new HashMap<>();
            newField.put("position", "디자이너");
            db.collection("project")
                    .document(projectId)
                    .collection("team")
                    .document(teamName)
                    .collection("members")
                    .document(individualItem.getUid())
                    .set(newField)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            int apply_designers = teamListItems.get(position).getApply_designers()+1;
                            db.collection("project")
                                    .document(projectId)
                                    .collection("team")
                                    .document(teamName)
                                    .update("apply_designers",apply_designers);
                            teamListItems.get(position).setApply_designers(apply_designers);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

            HashMap<String, Object> newMyProjectField = new HashMap<>();
            newMyProjectField.put("team_name",teamName);
            db.collection("individual")
                    .document(individualItem.getUid())
                    .collection("my_project")
                    .document(projectId)
                    .set(newMyProjectField)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

        }

    }
}