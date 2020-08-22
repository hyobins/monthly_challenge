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
//
//        ImageView imageView = (ImageView)view.findViewById(R.id.poster);
//        TextView movieName = (TextView)view.findViewById(R.id.movieName);
//        TextView grade = (TextView)view.findViewById(R.id.grade);
//
//        imageView.setImageResource(sample.get(position).getPoster());
//        movieName.setText(sample.get(position).getMovieName());
//        grade.setText(sample.get(position).getGrade());

        return view;
    }
}