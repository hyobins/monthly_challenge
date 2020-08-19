package Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.monthly_challenge.MainActivity;
import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*일반(개인) 회원 로그인 페이지 입니다. */

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void To_Signup_Page(View view){
        Intent intent = new Intent(getApplicationContext(), SignupTypeActivity.class);
        startActivity(intent);
    }

    public void Click_Login(View view){
        if (!binding.editTextEmail.getText().toString().equals("") && !binding.editTextPassword.getText().toString().equals("")) {
            loginUser(binding.editTextEmail.getText().toString(), binding.editTextPassword.getText().toString());
        } else {
            Toast.makeText(LoginActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
        }

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                }
            }
        };
    }

    public void loginUser(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //로그인 성공
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            firebaseAuth.addAuthStateListener(firebaseAuthListener);
                        } else{
                            //로그인 실패
                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuthListener != null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}
