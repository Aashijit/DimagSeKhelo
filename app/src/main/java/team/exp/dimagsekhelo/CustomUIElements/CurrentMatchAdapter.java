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

import team.exp.dimagsekhelo.Database.BusinessLogic.PointGenerationSystem;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceResponseObjects.CurrentMatchPoints;


public class CurrentMatchAdapter extends ArrayAdapter<CurrentMatchPoints> implements Codes {

    private final Activity context;
    private List<CurrentMatchPoints> currentMatchPointsList;
    private String matchType;
    private PointGenerationSystem pointGenerationSystem;

    public CurrentMatchAdapter(Activity context, List<CurrentMatchPoints> currentMatchPointsList,String matchType, TextView textView) {
        super(context, R.layout.point, currentMatchPointsList);
        this.context = context;
        this.matchType = matchType;
        this.currentMatchPointsList = currentMatchPointsList;
        pointGenerationSystem = new PointGenerationSystem();
    }


    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.point, null, true);

        final ImageView playerType = (ImageView) rowView.findViewById(R.id.pointSystemPlayerType);
        final TextView playerName = (TextView) rowView.findViewById(R.id.pointSystemPlayerName);
        final TextView playerRuns = (TextView) rowView.findViewById(R.id.pointSystemRuns);
        final TextView ballsFaced = (TextView) rowView.findViewById(R.id.pointSystemBallsFaced);
        final ImageView battingStatus = (ImageView) rowView.findViewById(R.id.pointSystemBattingStatus);
        final TextView wicketsTaken = (TextView) rowView.findViewById(R.id.pointSystemWicketsTaken);
        final TextView oversBowled = (TextView) rowView.findViewById(R.id.pointSystemOversBowled);
        final ImageView bowlingStatus = (ImageView) rowView.findViewById(R.id.pointSystemBowlingStatus);
        final TextView pointsEarned = (TextView) rowView.findViewById(R.id.pointSystemPointsEarned);

        CurrentMatchPoints currentMatchPoints = currentMatchPointsList.get(position);

        if(currentMatchPoints.get_PlayerType().equalsIgnoreCase(BATSMAN))
            playerType.setImageResource(R.drawable.batsman);
        else if(currentMatchPoints.get_PlayerType().equalsIgnoreCase(BOWLER))
            playerType.setImageResource(R.drawable.bowler);
        else if(currentMatchPoints.get_PlayerType().equalsIgnoreCase(ALL_ROUNDER))
            playerType.setImageResource(R.drawable.allrounder);

        playerName.setText(currentMatchPoints.get_PlayerName());
        playerRuns.setText(currentMatchPoints.get_RunsScored());
        ballsFaced.setText((Double.parseDouble(currentMatchPoints.get_OversFaced()) * 6.00d)+"");
        if(currentMatchPoints.get_BattingStatus().equalsIgnoreCase(BATTING_STATUS_BATTING))
        {
            battingStatus.setImageResource(R.drawable.ic_on);
            animateImage(battingStatus);
        }
        else
            battingStatus.setImageResource(R.drawable.ic_off);

//        wicketsTaken.setText(currentMatchPoints.getWicketsTaken().size());
        oversBowled.setText(currentMatchPoints.get_BowledOvers());
        if(currentMatchPoints.get_BowlingStatus().equalsIgnoreCase(BOWLING_STATUS_BOWLING))
        {
            bowlingStatus.setImageResource(R.drawable.ic_on);
            animateImage(battingStatus);
        }
        else
            bowlingStatus.setImageResource(R.drawable.ic_off);

        Double points = 0.00d;
        if(matchType.equalsIgnoreCase(MATCH_TYPE_ODI))
            points = pointGenerationSystem.getPointsForODI(currentMatchPoints);
        else if(matchType.equalsIgnoreCase(MATCH_TYPE_T20))
            points = pointGenerationSystem.getPointsForODI(currentMatchPoints);

        pointsEarned.setText(points+"");

        return rowView;
    }

    public void animateImage(ImageView imageView) {
        Animation animation1 =
                AnimationUtils.loadAnimation(context,
                        R.anim.blink);
        imageView.startAnimation(animation1);
    }
}