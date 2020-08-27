package Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityMypointBinding;

public class MypointActivity extends AppCompatActivity {
    ActivityMypointBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypoint);
        binding.setActivity(this);
    }

    public void BackButton(View view){
        finish();
    }
}