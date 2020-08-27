package Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityIndivSignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class IndivSignupActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityIndivSignupBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> individual = new HashMap<>();
    Map<String, Object> my_project = new HashMap<>();
    Map<String, Object> progress_project = new HashMap<>();
    Map<String, Object> judge_project = new HashMap<>();
    Map<String, Object> end_project = new HashMap<>();
    Button prev_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_indiv_signup);
        binding.setActivity(this);
        firebaseAuth = FirebaseAuth.getInstance();
        prev_btn = binding.buttonDeveloper;
        binding.buttonDesigner.setOnClickListener(this);
        binding.buttonDeveloper.setOnClickListener(this);
        binding.imageViewBack.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);
    }

    public void Click_Join(View view){
        String email = binding.editTextEmail .getText().toString().trim();
        String pw = binding.editTextPassword.getText().toString().trim();

        if (!email.equals("") && !pw.equals((""))) {
            createUser(email,pw);

        } else {
            Toast.makeText(IndivSignupActivity.this, "email과 비밀번호를 입력하세요.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void createUser(String email, String password) {
        //라디오 버튼 값 가져오기
//        int id = binding.radioGroup.getCheckedRadioButtonId();
//        RadioButton rb = findViewById(id);

        final String name = binding.editTextName.getText().toString().trim();
        final String position = prev_btn.getText().toString().trim();
        final String introduce = binding.editTextIntroduce.getText().toString().trim();
        final String interest = binding.editTextInterest.getText().toString().trim();
        final String profile_url = binding.profileUrl.getText().toString().trim();
        final String virtual_account = binding.virtualAccount.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //회원가입 성공시
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            individual.put("email",user.getEmail());
                            individual.put("name",name);
                            individual.put("position",position);
                            individual.put("introduce",introduce);
                            individual.put("interest",interest);
                            individual.put("profile_url",profile_url);
                            individual.put("virtual_account",virtual_account);

                            LoginActivity.addCollection("individual",user.getUid(),individual);
                            LoginActivity.addSubCollection("individual",user.getUid(), "my_project");
                            //document안에 field : project_id, project_state

                            Intent intent = new Intent(IndivSignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //계정 중복, 그외 다른 오류로 등록을 실패한 경우
                            Log.w("오류", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(IndivSignupActivity.this, "등록 실패",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_developer :
                prev_btn.setBackgroundResource(R.drawable.round_rectangle);
                binding.buttonDeveloper.setBackgroundResource(R.drawable.round_blue);
                prev_btn = binding.buttonDeveloper;
                break;
            case R.id.button_designer :
                prev_btn.setBackgroundResource(R.drawable.round_rectangle);
                binding.buttonDesigner.setBackgroundResource(R.drawable.round_blue);
                prev_btn = binding.buttonDesigner;
                break;
            case R.id.imageView_back:
                Intent intent = new Intent(IndivSignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_cancel:
                intent = new Intent(IndivSignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
