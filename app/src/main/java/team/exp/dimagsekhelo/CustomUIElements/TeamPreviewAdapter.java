package team.exp.dimagsekhelo.CustomUIElements;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceResponseObjects.PlayerResponse;

import static team.exp.dimagsekhelo.Utils.Codes.ALL_ROUNDER;
import static team.exp.dimagsekhelo.Utils.Codes.BATSMAN;
import static team.exp.dimagsekhelo.Utils.Codes.BOWLER;

public class TeamPreviewAdapter extends ArrayAdapter<PlayerResponse> {

    private Activity context;
    private List<PlayerResponse> playerResponses;
    private DateUtils dateUtils;

    public TeamPreviewAdapter(Activity context, List<PlayerResponse> playerResponses) {
        super(context, R.layout.playerselection, playerResponses);
        this.context=context;
        this.playerResponses = playerResponses;

        dateUtils = new DateUtils();
    }


    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.teampreviewrow, null,true);

        final ImageView playerType = (ImageView) rowView.findViewById(R.id.teamPreviewPlayerType);
        final TextView captainSelectionPlayerName = (TextView) rowView.findViewById(R.id.teamPreviewPlayerName);
        final TextView playerStatus = (TextView) rowView.findViewById(R.id.captainViceCaptainSelection);
        final TextView playerCredit = (TextView) rowView.findViewById(R.id.teamPreviewPlayerCredit);


        if(playerResponses.get(position).get_PlayerType().equalsIgnoreCase(BATSMAN))
            playerType.setImageResource(R.drawable.batsman);
        else if(playerResponses.get(position).get_PlayerType().equalsIgnoreCase(BOWLER))
            playerType.setImageResource(R.drawable.bowler);
        else if(playerResponses.get(position).get_PlayerType().equalsIgnoreCase(ALL_ROUNDER))
            playerType.setImageResource(R.drawable.allrounder);


        captainSelectionPlayerName.setText(playerResponses.get(position).get_PlayerName());
        String status="P";
        if(playerResponses.get(position).getPlayerCaptain() != null)
            if(playerResponses.get(position).getPlayerCaptain())
                status="C";

        if(playerResponses.get(position).getPlayerViceCaptain() != null)
            if(playerResponses.get(position).getPlayerViceCaptain())
                status="VC";

            playerStatus.setText(status);

            playerCredit.setText(playerResponses.get(position).get_PlayerCredit());

        return rowView;
    }

    public void animateText(TextView textView) {
        Animation animation1 =
                AnimationUtils.loadAnimation(context,
                        R.anim.blink);
        textView.startAnimation(animation1);
    }


}
