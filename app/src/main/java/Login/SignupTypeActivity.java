package Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivitySignupTypeBinding;


public class SignupTypeActivity extends AppCompatActivity {
    ActivitySignupTypeBinding binding; //자주 오류나는 곳 : 현재 안드로이드 스튜디오 표기법에서는 이름표기를 Activity-부터 시작하면됨

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_type);
        binding.setActivity(this);
    }

    public void Individual_SignUp(View view){
        Intent intent = new Intent(getApplicationContext(), IndivSignupActivity.class);
        startActivity(intent);
    }

    public void Business_SignUp(View view){
        Intent intent = new Intent(getApplicationContext(), BizSignupActivity.class);
        startActivity(intent);
    }



}