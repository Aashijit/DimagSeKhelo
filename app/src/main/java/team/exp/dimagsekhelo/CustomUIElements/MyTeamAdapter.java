package team.exp.dimagsekhelo.CustomUIElements;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceRequestObjects.TeamContestRequest;

public class MyTeamAdapter extends ArrayAdapter<TeamContestRequest> {

    private final Activity context;
    private List<TeamContestRequest> teamContestRequests;
    private DateUtils dateUtils;

    public MyTeamAdapter(Activity context, List<TeamContestRequest> teamContestRequests) {
        super(context, R.layout.myteams, teamContestRequests);
        this.context=context;
        this.teamContestRequests = teamContestRequests;
        dateUtils = new DateUtils();
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.myteams, null,true);

        final TextView teamName = (TextView) rowView.findViewById(R.id.matchCaption);
        final ImageButton button = (ImageButton) rowView.findViewById(R.id.editTeam);

        teamName.setText(teamContestRequests.get(position).get_TeamName());

        button.setEnabled(false);

        return rowView;
    }

    public void animateText(TextView textView) {
        Animation animation1 =
                AnimationUtils.loadAnimation(context,
                        R.anim.blink);
        textView.startAnimation(animation1);
    }
}