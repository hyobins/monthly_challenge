package com.example.monthly_challenge.BottomNavigationFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.monthly_challenge.MainActivity;
import com.example.monthly_challenge.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Login.LoginActivity;
import Profile.IndividualItem;
import Profile.MyprojectActivity;
import Profile.ProfileActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingFragment extends Fragment implements View.OnClickListener{
    ViewGroup viewGroup;
    @BindView(R.id.profile) LinearLayout profile;
    @BindView(R.id.my_project) LinearLayout my_project;
    @BindView(R.id.my_point) LinearLayout my_point;
    @BindView(R.id.logout) LinearLayout logout;

    @BindView(R.id.name) TextView name;
    @BindView(R.id.email) TextView email;
    @BindView(R.id.user_position) TextView position;

    IndividualItem individualItem;

    Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, viewGroup);

        profile.setOnClickListener(this);
        my_project.setOnClickListener(this);
        my_point.setOnClickListener(this);
        logout.setOnClickListener(this);

        individualItem = MainActivity.getIndividualItem();
        position.setText(individualItem.getPosition());
        name.setText(individualItem.getName());
        email.setText(individualItem.getEmail());
        intent = new Intent(getActivity() , ProfileActivity.class);
        intent.putExtra("position", individualItem.getPosition());
        intent.putExtra("name", individualItem.getName());
        intent.putExtra("email", individualItem.getEmail());

        intent.putExtra("introduce",individualItem.getIntroduce());
        intent.putExtra("interest",individualItem.getInterest());
        intent.putExtra("profile_url",individualItem.getProfile_url());

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = user.getUid();
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference reference = db.collection("individual").document(uid);
//        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if(document != null){
//
//                        position.setText(document.getString("position"));
//                        name.setText(document.getString("name"));
//                        email.setText(document.getString("email"));
//
//                        intent = new Intent(getActivity() , ProfileActivity.class);
//                        intent.putExtra("position", document.getString("position"));
//                        intent.putExtra("name", document.getString("name"));
//                        intent.putExtra("email", document.getString("email"));
//
//                        intent.putExtra("introduce",document.getString("introduce"));
//                        intent.putExtra("interest",document.getString("interest"));
//                        intent.putExtra("profile_url",document.getString("profile_url"));
//
//                    }
//                }
//            }
//        });

        return viewGroup;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.profile:

                startActivity(intent);
                break;
            case R.id.my_project:
                Intent myproject_intent = new Intent(getActivity(), MyprojectActivity.class);
                startActivity(myproject_intent);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent logout_intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(logout_intent);
                break;
        }
    }
}
