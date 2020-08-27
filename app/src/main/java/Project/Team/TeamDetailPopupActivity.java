package Project.Team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityTeamDetailPopupBinding;
import com.example.monthly_challenge.databinding.ActivityTeampopupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import Profile.IndividualItem;
import Project.ProjectDetailActivity;

public class TeamDetailPopupActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTeamDetailPopupBinding binding;
    int position;
    ArrayList<MemberListItem> members;
    ArrayList<TeamListItem> team;
    String teamName;
    String projectId;
    int k;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> MemberIDLists = new ArrayList<String>();

    MemberListAdapter memberListAdapter;
    MemberListItem memberListItem;
    ArrayList<MemberListItem> memberListItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_detail_popup);
        binding.setActivity(this);
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        teamName = intent.getExtras().getString("teamName");
        projectId = intent.getExtras().getString("projectId");

        members = new ArrayList<>();
        team = new ArrayList<>();
        team.add(ProjectDetailActivity.getTeamListItem(position)) ;

        db.collection("project")
                .document(projectId)
                .collection("team")
                .document(teamName)
                .collection("members")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                                MemberIDLists.add(document.getId());
                                Log.d("멤버", String.valueOf(MemberIDLists.size()));
                            }
                            db.collection("individual")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for(DocumentSnapshot document2 : Objects.requireNonNull(task.getResult())){
                                                for(int i=0;i<MemberIDLists.size();i++){
                                                    if(document2.getId().equals(MemberIDLists.get(i))){
                                                        Log.d("일치", MemberIDLists.get(i));

                                                        db.collection("individual")
                                                                .document(MemberIDLists.get(i))
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if(task.isSuccessful()){
                                                                            DocumentSnapshot document3 = task.getResult();
                                                                            Log.d("document3",document3.getString("position"));
                                                                            memberListItem = new MemberListItem(
                                                                                    document3.getString("position"),
                                                                                    document3.getString("name"),
                                                                                    document3.getString("email"),
                                                                                    document3.getString("profile_url"),
                                                                                    document3.getString("introduce"));
                                                                            memberListItems.add(memberListItem);
                                                                            System.out.println("사이즈"+memberListItems.size());
                                                                        }
                                                                        memberListAdapter = new MemberListAdapter(getApplicationContext(), memberListItems);
                                                                        binding.listViewTeamMembers.setAdapter(memberListAdapter);
                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        }
                                    });
                        }
//                        memberListAdapter = new MemberListAdapter(getApplicationContext(), memberListItems);
//                        binding.listViewTeamMembers.setAdapter(memberListAdapter);
                    }
                });

        TeamListAdapter teamListAdapter = new TeamListAdapter(getApplicationContext(), team);
        binding.listViewTeamInfo.setAdapter(teamListAdapter);
        binding.imageViewBack.setOnClickListener(this);
        binding.buttonApply.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_back :
                finish();
                break;
            case R.id.button_apply :
                Intent intent = new Intent();
                intent.putExtra("position",position);
                intent.putExtra("teamName",teamName);

                setResult(RESULT_OK, intent);

                finish();

                break;
        }
    }
}