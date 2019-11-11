package team.exp.dimagsekhelo.CustomUIElements;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.exp.dimagsekhelo.DataObject.Leaderboard;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceResponseObjects.UpcomingMatchesResponse;

public class LeaderboardAdapter extends ArrayAdapter<Leaderboard> {

    private final Activity context;
    private List<Leaderboard> leaderboardList;
    private DateUtils dateUtils;

    public LeaderboardAdapter(Activity context, List<Leaderboard> leaderboardList) {
        super(context, R.layout.leaderboard, leaderboardList);
        this.context=context;
        this.leaderboardList=leaderboardList;
        dateUtils = new DateUtils();
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.leaderboard, null,true);

        final TextView leaderboardUserPhoneNumber = (TextView) rowView.findViewById(R.id.leaderboardUserPhoneNumber);
        final TextView leaderboardPoints = (TextView) rowView.findViewById(R.id.leaderboardPoints);
        final TextView leaderboardRanks = (TextView) rowView.findViewById(R.id.leaderboardRanks);

        leaderboardUserPhoneNumber.setText(leaderboardList.get(position).get_PhoneNumber());
        leaderboardPoints.setText(leaderboardList.get(position).get_Ranks());
        leaderboardRanks.setText(leaderboardList.get(position).get_Score());


        return rowView;
    }

    public void animateText(TextView textView) {
        Animation animation1 =
                AnimationUtils.loadAnimation(context,
                        R.anim.blink);
        textView.startAnimation(animation1);
    }



}
