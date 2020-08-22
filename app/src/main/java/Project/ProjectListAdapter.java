package Project;
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

import Project.Team.TeamListItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectListAdapter extends BaseAdapter {

    Context context = null;
    LayoutInflater layoutInflater = null;
    ArrayList<ProjectListItem> projectListItems;
    @BindView(R.id.textView_listCompany) TextView textView_listCompany;
    @BindView(R.id.textView_listTitle) TextView textView_listTitle;
    @BindView(R.id.textView_listDeadline) TextView textView_listDeadline;
    @BindView(R.id.textView_listReward) TextView textView_listReward;

    public ProjectListAdapter(Context context, ArrayList<ProjectListItem> data) {
        context = context;
        projectListItems = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return projectListItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ProjectListItem getItem(int position) {
        return projectListItems.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.listview_project, null);
        ButterKnife.bind(this, view);

        textView_listCompany.setText(projectListItems.get(position).getCompany());
        textView_listTitle.setText(projectListItems.get(position).getTitle());
        textView_listDeadline.setText(projectListItems.get(position).getDeadline());
        textView_listReward.setText(projectListItems.get(position).getReward());
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