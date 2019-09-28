package team.exp.dimagsekhelo.CustomUIElements;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.WebServiceResponseObjects.UpcomingMatchesResponse;


public class UpcomingMatchesListAdapter extends ArrayAdapter<UpcomingMatchesResponse> {

    private final Activity context;
    private List<UpcomingMatchesResponse> upcomingMatchesResponseList;

    public UpcomingMatchesListAdapter(Activity context, List<UpcomingMatchesResponse> upcomingMatchesResponseList) {
        super(context, R.layout.upcomingmatcheslist, upcomingMatchesResponseList);
        this.context=context;
        this.upcomingMatchesResponseList = upcomingMatchesResponseList;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.upcomingmatcheslist, null,true);

        final TextView upcomingMatchesCaption = (TextView) rowView.findViewById(R.id.upcomingMatchesCaption);
        final ImageView imageViewTeamName1 = (ImageView) rowView.findViewById(R.id.upcomingMatchesTeamName1Image);
        final TextView upcomingMatchesTeamName1 = (TextView) rowView.findViewById(R.id.upcomingMatchesTeamName1);
        final TextView upcomingMatchesTime = (TextView) rowView.findViewById(R.id.upcomingMatchesMatchTime);
        final TextView upcomingMatchesTeamName2 = (TextView) rowView.findViewById(R.id.upcomingMatchesTeamName2);
        final ImageView imageViewTeamName2 = (ImageView) rowView.findViewById(R.id.upcomingMatchesTeamName2Image);

        upcomingMatchesCaption.setText(upcomingMatchesResponseList.get(position).get_Caption());
        upcomingMatchesTeamName1.setText(upcomingMatchesResponseList.get(position).get_TeamName1());
        upcomingMatchesTime.setText(upcomingMatchesResponseList.get(position).get_MatchTime());
        upcomingMatchesTeamName2.setText(upcomingMatchesResponseList.get(position).get_TeamName2());

        return rowView;
    }
}