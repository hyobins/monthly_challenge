package Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityMyprojectBinding;
import com.example.monthly_challenge.databinding.ActivityMyprojectDetailBinding;
import com.example.monthly_challenge.databinding.ActivityProjectdetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import Project.ProjectDetailActivity;
import Project.Team.TeamListAdapter;
import Project.Team.TeamListItem;
import Project.Team.TeamPopupActivity;

public class MyprojectDetailActivity extends AppCompatActivity implements View.OnClickListener{
    Context context = this;
    Intent intent;
    ActivityMyprojectDetailBinding binding;
    String projectId;
    LinearLayout prevLayout;
    View prevView;
    TextView prevText;

    String teamName;
    ArrayList<String> teamNames;
    TeamListItem teamListItem;
    static ArrayList<TeamListItem> teamListItems;
    TeamListAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_myproject_detail);
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

        prevLayout = binding.infoLayout;
        prevView = binding.infoView;
        prevText = binding.infoText;

        //teamNames = new ArrayList<String>();

        teamListItems = new ArrayList<TeamListItem>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("individual")
                .document(user.getUid())
                .collection("my_project")
                .document(projectId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            teamName = Objects.requireNonNull(document.get("team_name")).toString();
                            binding.myTeamName.setText(teamName);

                            db.collection("project")
                                    .document(projectId)
                                    .collection("team")
                                    .document(teamName)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                DocumentSnapshot document = task.getResult();
                                                teamListItem = new TeamListItem(document.getId(), Integer.valueOf(document.get("max_designers").toString()),Integer.valueOf(document.get("max_developers").toString())
                                                        ,Integer.valueOf(document.get("apply_designers").toString()),Integer.valueOf(document.get("apply_developers").toString()), document.get("openchat_url").toString());
                                                teamListItems.add(teamListItem);
                                            }
                                            adapter = new TeamListAdapter(context, teamListItems);
                                            binding.listViewTeamList.setAdapter(adapter);
                                        }
                                    });
                        }
                    }
                });

        db.collection("project")
                .document(projectId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document2 = task.getResult();
                            String submit_email = document2.getString("submit");
                            binding.submitEmail.setText(submit_email);

                            binding.textViewCompanyContents.setText(document2.getString("company"));
                            String temp = document2.getString("description").replace("*","\n");
                            binding.textViewDescriptionContents.setText(temp);
                            binding.textViewDirectionContents.setText(document2.getString("direction"));

                        }
                    }
                });



        binding.listViewTeamList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent Team_intent = new Intent(context, MyteamsActivity.class);

                Team_intent.putExtra("projectId",projectId); /*송신*/
                Team_intent.putExtra("teamName",teamName);

                startActivity(Team_intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
//                System.out.println(data.getStringExtra("new_teamName") + ", " + Integer.valueOf(data.getStringExtra("new_maxDesigners")) + ", " + data.getStringExtra("new_openchatUrl"));
                teamListItem = new TeamListItem(data.getStringExtra("new_teamName"), data.getExtras().getInt("new_maxDesigners")
                        , data.getExtras().getInt("new_maxDevelopers"), data.getExtras().getInt("new_applyDesigners")
                        , data.getExtras().getInt("new_applyDevelopers"), data.getStringExtra("new_openchatUrl"));
                teamListItems.add(teamListItem);
                adapter.notifyDataSetChanged();
            }
        }
    }


}