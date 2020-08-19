package Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityLoginTypeBinding;


public class LoginTypeActivity extends AppCompatActivity {
    ActivityLoginTypeBinding binding; //자주 오류나는 곳 : 현재 안드로이드 스튜디오 표기법에서는 이름표기를 Activity-부터 시작하면됨

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_type);
        binding.setActivity(this);
    }

    public void Individual_Login(View view){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    //[수정 후 주석삭제] 현재 기업 로그인도 일반 로그인 페이지로 넘어가도록 되어있음.
    public void Buisness_Login(View view){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }



}