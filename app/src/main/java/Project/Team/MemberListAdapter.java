package Project.Team;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.monthly_challenge.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberListAdapter extends BaseAdapter {
    @BindView(R.id.listview_position) TextView positions;
//    @BindView(R.id.listview_name) TextView name;
    @BindView(R.id.listview_email) TextView email;
    @BindView(R.id.listview_profileurl) TextView profile_url;
    @BindView(R.id.listview_introduce) TextView introduce;

    Context context = null;
    LayoutInflater layoutInflater = null;
    ArrayList<MemberListItem> memberListItems;

    public MemberListAdapter(Context context, ArrayList<MemberListItem> data) {
        memberListItems = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return memberListItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MemberListItem getItem(int position) {
        return memberListItems.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.listview_members, null);
        ButterKnife.bind(this, view);

        positions.setText(memberListItems.get(position).getMemberPosition());
//        name.setText(memberListItems.get(position).getMemberName());
        email.setText(memberListItems.get(position).getMemberEmail());
        profile_url.setText(memberListItems.get(position).getMemberProfile());
        introduce.setText(memberListItems.get(position).getMemberIntroduce());



        return view;
    }
}
