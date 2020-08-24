package Project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityProjectdetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.okhttp.internal.Internal;

import java.util.ArrayList;

import Project.Team.TeamListAdapter;
import Project.Team.TeamListItem;

public class ProjectDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    Intent intent;
    ActivityProjectdetailBinding binding;
    String projectId;
    LinearLayout prevLayout;
    View prevView;
    TextView prevText;

    String teamName;
    ArrayList<String> teamNames;
    TeamListItem teamListItem;
    ArrayList<TeamListItem> teamListItems;
    TeamListAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        binding.buttonCreatTeam.setOnClickListener(this);
        prevLayout = binding.infoLayout;
        prevView = binding.infoView;
        prevText = binding.infoText;

        teamNames = new ArrayList<String>();
        teamListItems = new ArrayList<TeamListItem>();

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
                                                                ,Integer.valueOf(document.get("apply_designers").toString()),Integer.valueOf(document.get("apply_developers").toString()));
                                teamListItems.add(teamListItem);
                            }
                            adapter = new TeamListAdapter(context, teamListItems);
                            binding.listViewTeamList.setAdapter(adapter);
                        }
                        else{

                        }
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
            case R.id.button_creatTeam:

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

}