package Project.Team;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.monthly_challenge.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamListAdapter extends BaseAdapter {
    @BindView(R.id.textView_teamName) TextView textView_teamName;
    @BindView(R.id.textView_developers) TextView textView_developers;
    @BindView(R.id.textView_designers) TextView textView_designers;
    @BindView(R.id.imageView_designer1) ImageView imageView_designer1;
    @BindView(R.id.imageView_designer2) ImageView imageView_designer2;
    @BindView(R.id.imageView_designer3) ImageView imageView_designer3;
    @BindView(R.id.imageView_developer1) ImageView imageView_developer1;
    @BindView(R.id.imageView_developer2) ImageView imageView_developer2;
    @BindView(R.id.imageView_developer3) ImageView imageView_developer3;



    Context context = null;
    LayoutInflater layoutInflater = null;
    ArrayList<TeamListItem> teamListItems;

    public TeamListAdapter(Context context, ArrayList<TeamListItem> data) {
        context = context;
        teamListItems = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return teamListItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public TeamListItem getItem(int position) {
        return teamListItems.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.listview_team, null);
        ButterKnife.bind(this, view);

        textView_teamName.setText(teamListItems.get(position).getTeamName());
        textView_developers.setText(teamListItems.get(position).getApply_developers() + "/" + teamListItems.get(position).getMax_developers());
        textView_designers.setText(teamListItems.get(position).getApply_designers() + "/" + teamListItems.get(position).getMax_designers());

        switch (teamListItems.get(position).getMax_developers()){
            case 1 :
                imageView_developer2.setVisibility(View.INVISIBLE);
            case 2 :
                imageView_developer1.setVisibility(View.INVISIBLE);
                break;
        }

        switch (teamListItems.get(position).getMax_designers()){
            case 1 :
                imageView_designer2.setVisibility(View.INVISIBLE);
            case 2 :
                imageView_designer1.setVisibility(View.INVISIBLE);
                break;
        }

        switch (teamListItems.get(position).getApply_developers()){
            case 3 :
                imageView_developer1.setImageResource(R.drawable.apply_user);
            case 2 :
                imageView_developer2.setImageResource(R.drawable.apply_user);
            case 1 :
                imageView_developer3.setImageResource(R.drawable.apply_user);
                break;
        }
        switch (teamListItems.get(position).getApply_designers()){
            case 3 :
                imageView_designer1.setImageResource(R.drawable.apply_user);
            case 2 :
                imageView_designer2.setImageResource(R.drawable.apply_user);
            case 1 :
                imageView_designer3.setImageResource(R.drawable.apply_user);
                break;
        }

        return view;
    }
}