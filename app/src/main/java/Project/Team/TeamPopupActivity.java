package Project.Team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.monthly_challenge.MainActivity;
import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityTeampopupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Profile.IndividualItem;
import Profile.ProfileActivity;
import Project.ProjectDetailActivity;

public class TeamPopupActivity extends Activity implements View.OnClickListener {
    ActivityTeampopupBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<TeamListItem> teamListItems;
    String final_teamName = "";
    int final_maxDevelopers = 1;
    int final_maxDesigners = 1;
    static int final_applyDevelopers = 0;
    int final_applyDesigners = 0;
    String final_openchatUrl = "";
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid ;
    String projectId;
    Map<String, Object> newTeam = new HashMap<>();
    Map<String, Object> newField = new HashMap<>();
//    CollectionReference teamCollectionReference;
    CollectionReference teamCollectionReference;

    IndividualItem individualItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teampopup);
        binding.setActivity(this);
        teamListItems = ProjectDetailActivity.getTeamListItems();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();
        Intent intent = getIntent();
        projectId = intent.getStringExtra("projectId");


        teamCollectionReference = db.collection("project").document(projectId).collection("team");

        binding.buttonCheck.setOnClickListener(this);
        binding.buttonCancel.setOnClickListener(this);
        binding.buttonCreate.setOnClickListener(this);
        binding.imageViewMinusDeveloper.setOnClickListener(this);
        binding.imageViewPlusDeveloper.setOnClickListener(this);
        binding.imageViewMinusDesigner.setOnClickListener(this);
        binding.imageViewPlusDesigner.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_check :
                String teamName = binding.editTextTeamName.getText().toString();
                for(int i=0;i<teamListItems.size();i++){
                    if(teamListItems.get(i).getTeamName().equals(teamName)){
                        Toast.makeText(getApplicationContext(), "이미 존재하는 이름 입니다",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else if(i == teamListItems.size()-1){
                        Toast.makeText(getApplicationContext(), "사용 가능한 이름 입니다", Toast.LENGTH_SHORT).show();
                        final_teamName = teamName;
                    }
                }

                break;
            case R.id.button_create :
                individualItem = MainActivity.getIndividualItem();
                final_openchatUrl = binding.editTextOpenchatUrl.getText().toString();
                final_maxDesigners = Integer.parseInt(binding.textViewMaxDesigners.getText().toString());
                final_maxDevelopers = Integer.parseInt(binding.textViewMaxDevelopers.getText().toString());
                if(final_teamName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "팀 이름을 정해 주세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                else if(final_openchatUrl.isEmpty()){
                    Toast.makeText(getApplicationContext(), "팀 오픈채팅 주소를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                newTeam.put("apply_designers",final_applyDesigners);
                newTeam.put("apply_developers",final_applyDevelopers);
                newTeam.put("max_designers",final_maxDesigners);
                newTeam.put("max_developers",final_maxDevelopers);
                newTeam.put("openchat_url",final_openchatUrl);

                teamCollectionReference.document(final_teamName)
                    .set(newTeam)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            System.out.println("팀 추가 완료");

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("팀 추가 실패 : " + e);

                        }
                    });
                if(individualItem.getPosition().equals("개발자")){
                    newField.put("position", "개발자");
                    teamCollectionReference.document(final_teamName)
                            .collection("members")
                            .document(individualItem.getUid())
                            .set(newField)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("왜 실패 ? : " + e);
                                }
                            });
                    teamCollectionReference.document(final_teamName).update("apply_developers",1);
                    final_applyDevelopers = 1;
                }
                else{
                    newField.put("position", "디자이너");
                    teamCollectionReference.document(final_teamName)
                            .collection("members")
                            .document(individualItem.getUid())
                            .set(newField)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("왜 실패 ? : " + e);
                                }
                            });
                    teamCollectionReference.document(final_teamName).update("apply_designers",1);
                    final_applyDesigners = 1;
                }

                Intent intent = new Intent();
                TeamListItem newTeamListItem= new TeamListItem(final_teamName, final_maxDesigners, final_maxDevelopers, final_applyDesigners,final_applyDevelopers,final_openchatUrl);
                intent.putExtra("new_teamName",final_teamName);
                intent.putExtra("new_maxDesigners", final_maxDesigners);
                intent.putExtra("new_maxDevelopers", final_maxDevelopers);
                intent.putExtra("new_applyDesigners", final_applyDesigners);
                intent.putExtra("new_applyDevelopers", final_maxDevelopers);
                intent.putExtra("new_openchatUrl",final_openchatUrl);
                System.out.println("apply Developers1 : " + final_applyDevelopers);
                setResult(RESULT_OK, intent);

                finish();


                break;
            case R.id.button_cancel :
                finish();

            case R.id.imageView_minusDeveloper :
                int max = Integer.parseInt(binding.textViewMaxDevelopers.getText().toString());
                if(max != 1){
                    max -= 1;
                    binding.textViewMaxDevelopers.setText(Integer.toString(max));
                    final_maxDevelopers--;
                    System.out.println("apply Developers1-1 : " + final_applyDevelopers);
                }
                break;
            case R.id.imageView_plusDeveloper :
                max = Integer.parseInt(binding.textViewMaxDevelopers.getText().toString());
                if(max != 3){
                    max += 1;
                    binding.textViewMaxDevelopers.setText(Integer.toString(max));
                    final_maxDevelopers++;
                    System.out.println("apply Developers1-2 : " + final_applyDevelopers);
                }
                break;
            case R.id.imageView_minusDesigner :
                max = Integer.parseInt(binding.textViewMaxDesigners.getText().toString());
                if(max != 1){
                    max -= 1;
                    binding.textViewMaxDesigners.setText(Integer.toString(max));
                    final_maxDesigners--;
                    System.out.println("apply Developers1-3 : " + final_applyDevelopers);
                }
                break;
            case R.id.imageView_plusDesigner :
                max = Integer.parseInt(binding.textViewMaxDesigners.getText().toString());
                if(max != 3){
                    max += 1;
                    binding.textViewMaxDesigners.setText(Integer.toString(max));
                    final_maxDesigners++;
                    System.out.println("apply Developers1-4 : " + final_applyDevelopers);
                }
                break;


        }
    }
    public static int getApplyDevelopers(){
        return final_applyDevelopers;
    }
}