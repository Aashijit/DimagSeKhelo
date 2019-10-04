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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceResponseObjects.PlayerResponse;

public class PlayerAdapter extends ArrayAdapter<PlayerResponse> implements Codes
{
    private final Activity context;
    private List<PlayerResponse> playerResponses;
    private DateUtils dateUtils;
    private TextView textView;

    public PlayerAdapter(Activity context, List<PlayerResponse> playerResponses, TextView textView) {
        super(context, R.layout.playerselection, playerResponses);
        this.context=context;
        this.textView = textView;
        this.playerResponses = playerResponses;
        dateUtils = new DateUtils();
    }


    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.playerselection, null,true);

        final ImageView playerType = (ImageView) rowView.findViewById(R.id.playerType);
        final TextView playerSelectionPlayerName = (TextView) rowView.findViewById(R.id.playerSelectionPlayerName);
        final TextView playerSelectionCredit = (TextView) rowView.findViewById(R.id.playerSelectionPlayerCredit);
        final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.playerSelectionCheckBox);

        if(playerResponses.get(position).get_PlayerType().equalsIgnoreCase(BATSMAN))
            playerType.setImageResource(R.drawable.batsman);
        else if(playerResponses.get(position).get_PlayerType().equalsIgnoreCase(BOWLER))
            playerType.setImageResource(R.drawable.bowler);
        else if(playerResponses.get(position).get_PlayerType().equalsIgnoreCase(ALL_ROUNDER))
            playerType.setImageResource(R.drawable.allrounder);


        playerSelectionPlayerName.setText(playerResponses.get(position).get_PlayerName());
        playerSelectionCredit.setText(playerResponses.get(position).get_PlayerCredit());
        checkBox.setChecked(playerResponses.get(position) != null && playerResponses.get(position).getPlayerSelected() != null ?  playerResponses.get(position).getPlayerSelected() : false );

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    //Step 1: Fetch the contents of the Text View
                    String contents = textView.getText() != null ? textView.getText().toString() : "100.00";

                    //Step 2: Parse the text view
                    double d = Double.parseDouble(contents);

                    if(isChecked)
                        d -= Double.parseDouble(playerResponses.get(position).get_PlayerCredit());
                    else
                        d += Double.parseDouble(playerResponses.get(position).get_PlayerCredit());

                    if(d<=0){
                        checkBox.setChecked(false);
                        return;
                    }

                    textView.setText(d+"");


                    Log.v(this.getClass().getName(),isChecked+": Value Fetched ");
                    playerResponses.get(position).setPlayerSelected(isChecked);
                    checkBox.setChecked(isChecked);
           }
        });
        return rowView;
    }

    public void animateText(TextView textView) {
        Animation animation1 =
                AnimationUtils.loadAnimation(context,
                        R.anim.blink);
        textView.startAnimation(animation1);
    }


}
