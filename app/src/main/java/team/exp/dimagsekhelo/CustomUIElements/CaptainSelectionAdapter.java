package team.exp.dimagsekhelo.CustomUIElements;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceResponseObjects.PlayerResponse;

import static team.exp.dimagsekhelo.Utils.Codes.ALL_ROUNDER;
import static team.exp.dimagsekhelo.Utils.Codes.BATSMAN;
import static team.exp.dimagsekhelo.Utils.Codes.BOWLER;

public class CaptainSelectionAdapter extends ArrayAdapter<PlayerResponse> {

    private  Activity context;
    private List<PlayerResponse> playerResponses;
    private DateUtils dateUtils;

    public CaptainSelectionAdapter(Activity context, List<PlayerResponse> playerResponses) {
        super(context, R.layout.playerselection, playerResponses);
        this.context=context;
        this.playerResponses = playerResponses;
        dateUtils = new DateUtils();
    }


    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.captainselection, null,true);

        final ImageView playerType = (ImageView) rowView.findViewById(R.id.captainSelectionPlayerType);
        final TextView captainSelectionPlayerName = (TextView) rowView.findViewById(R.id.captainSelectionPlayerName);
        final Button captainSelection = (Button) rowView.findViewById(R.id.captainSelection);
        final Button viceCaptainSelection = (Button) rowView.findViewById(R.id.viceCaptainSelection);



        if(playerResponses.get(position).get_PlayerType().equalsIgnoreCase(BATSMAN))
            playerType.setImageResource(R.drawable.batsman);
        else if(playerResponses.get(position).get_PlayerType().equalsIgnoreCase(BOWLER))
            playerType.setImageResource(R.drawable.bowler);
        else if(playerResponses.get(position).get_PlayerType().equalsIgnoreCase(ALL_ROUNDER))
            playerType.setImageResource(R.drawable.allrounder);


        captainSelectionPlayerName.setText(playerResponses.get(position).get_PlayerName());

        captainSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(captainSelected(playerResponses) && playerResponses.get(position).getPlayerCaptain()  == null)
                    return;
                if(captainSelected(playerResponses) && !playerResponses.get(position).getPlayerCaptain())
                    return;

                if(playerResponses.get(position).getPlayerCaptain() == null){
                    playerResponses.get(position).setPlayerCaptain(true);
                    if(view instanceof Button){
                        ((Button) view).setBackground(context.getResources().getDrawable(R.drawable.allsideborder));
                        ((Button) view).setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    }
                    viceCaptainSelection.setEnabled(false);
                }else{
                    if(playerResponses.get(position).getPlayerCaptain()){
                        //True
                        playerResponses.get(position).setPlayerCaptain(false);
                        if(view instanceof Button){
                            ((Button) view).setBackground(context.getResources().getDrawable(R.drawable.allsidebordernotfocussed));
                            ((Button) view).setTextColor(context.getResources().getColor(R.color.lightgrey));
                        }
                        viceCaptainSelection.setEnabled(true);
                    }
                    else{
                        //False
                        playerResponses.get(position).setPlayerCaptain(true);
                        if(view instanceof Button){
                            ((Button) view).setBackground(context.getResources().getDrawable(R.drawable.allsideborder));
                            ((Button) view).setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        }
                        viceCaptainSelection.setEnabled(false);
                    }
                }
            }
        });



        viceCaptainSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viceCaptainSelected(playerResponses) && playerResponses.get(position).getPlayerViceCaptain() == null )
                    return;

                if(viceCaptainSelected(playerResponses) && !playerResponses.get(position).getPlayerViceCaptain())
                    return;

                if(playerResponses.get(position).getPlayerViceCaptain() == null){
                    playerResponses.get(position).setPlayerViceCaptain(true);
                    if(view instanceof Button){
                        ((Button) view).setBackground(context.getResources().getDrawable(R.drawable.allsideborder));
                        ((Button) view).setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    }
                    captainSelection.setEnabled(false);
                }else{
                    if(playerResponses.get(position).getPlayerViceCaptain()){
                        //True
                        playerResponses.get(position).setPlayerViceCaptain(false);
                        if(view instanceof Button){
                            ((Button) view).setBackground(context.getResources().getDrawable(R.drawable.allsidebordernotfocussed));
                            ((Button) view).setTextColor(context.getResources().getColor(R.color.lightgrey));
                        }
                        captainSelection.setEnabled(true);
                    }
                    else{
                        //False
                        playerResponses.get(position).setPlayerViceCaptain(true);
                        if(view instanceof Button){
                            ((Button) view).setBackground(context.getResources().getDrawable(R.drawable.allsideborder));
                            ((Button) view).setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        }
                        captainSelection.setEnabled(false);
                    }
                }
            }
        });

        return rowView;
    }

    public boolean captainSelected(List<PlayerResponse> playerResponses){
        for(PlayerResponse playerResponse : playerResponses)
            if(playerResponse.getPlayerCaptain()!= null)
                if(playerResponse.getPlayerCaptain())
                    return true;
        return false;
    }

    public boolean viceCaptainSelected(List<PlayerResponse> playerResponses){
        for(PlayerResponse playerResponse : playerResponses)
            if(playerResponse.getPlayerViceCaptain() != null)
                if(playerResponse.getPlayerViceCaptain())
                    return true;
        return false;
    }

    public void animateText(TextView textView) {
        Animation animation1 =
                AnimationUtils.loadAnimation(context,
                        R.anim.blink);
        textView.startAnimation(animation1);
    }


}
