package Project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.monthly_challenge.R;
import com.example.monthly_challenge.databinding.ActivityProjectdetailBinding;

public class ProjectDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    ActivityProjectdetailBinding binding;
    String title;
    String deadline;
    String reward;
    LinearLayout prevLayout;
    View prevView;
    TextView prevText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_projectdetail);
        binding.setActivity(this);

        intent = getIntent();
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