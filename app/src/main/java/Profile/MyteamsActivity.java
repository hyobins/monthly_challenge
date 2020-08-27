package Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.monthly_challenge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import Project.Team.MemberListAdapter;
import Project.Team.MemberListItem;
import Project.Team.TeamListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.System.*;

public class MyteamsActivity extends AppCompatActivity implements View.OnClickListener{
    Context context = this;
    String projectId;
    String teamName;

    @BindView(R.id.MyTeamsList)
    ListView myTeamsList;

    @BindView(R.id.imageView_back) ImageView imageView_back;

    //members id 임시저장
    ArrayList<String> MemberIDLists = new ArrayList<String>();

    MemberListAdapter memberListAdapter;
    MemberListItem memberListItem;
    ArrayList<MemberListItem> memberListItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myteams);
        ButterKnife.bind(this);
        imageView_back.setOnClickListener(this);

        Intent intent = getIntent();
        projectId = intent.getExtras().getString("projectId");
        teamName = intent.getExtras().getString("teamName");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                                                                        memberListAdapter = new MemberListAdapter(context, memberListItems);
                                                                        myTeamsList.setAdapter(memberListAdapter);
                                                                    }

                                                                });

                                                    }
                                                }
                                            }

                                        }
                                    });
                        }

                    }
                });




    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView_back:
                finish();
        }
    }
}