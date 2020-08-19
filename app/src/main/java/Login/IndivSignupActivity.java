package Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityIndivSignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class IndivSignupActivity extends AppCompatActivity {
    ActivityIndivSignupBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_indiv_signup);
        binding.setActivity(this);
        firebaseAuth = FirebaseAuth.getInstance();
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
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //회원가입 성공시
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



}