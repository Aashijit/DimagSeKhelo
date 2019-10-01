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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceResponseObjects.ContestMasterResponse;

public class ContestMasterAdapter extends ArrayAdapter<ContestMasterResponse> {

    private final Activity context;
    private List<ContestMasterResponse> contestMasterResponses;
    private DateUtils dateUtils;

    public ContestMasterAdapter(Activity context, List<ContestMasterResponse> contestMasterResponses) {
        super(context, R.layout.contest, contestMasterResponses);
        this.context=context;
        this.contestMasterResponses = contestMasterResponses;
        dateUtils = new DateUtils();
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.contest, null,true);

        final TextView contestPrizePool = (TextView) rowView.findViewById(R.id.contestPrizePool);
        final TextView contestTotalSpots = (TextView) rowView.findViewById(R.id.contestTotalSpots);
        final TextView contestSpotsRemaining = (TextView) rowView.findViewById(R.id.contestSpotsRemaining);
        final ProgressBar progressBarSpotsRemaining = (ProgressBar) rowView.findViewById(R.id.contestSpotsRemainingBar);
        final TextView contestsTotalWinners = (TextView) rowView.findViewById(R.id.contestTotalWinners);
        final TextView contestType = (TextView) rowView.findViewById(R.id.contestType);
        final Button contestButton = (Button) rowView.findViewById(R.id.contestEntryFee);


        contestPrizePool.setText(context.getString(R.string.Rs)+contestMasterResponses.get(position).get_PrizePool());
        contestTotalSpots.setText(contestMasterResponses.get(position).get_TotalStrength()+" Spots");
        progressBarSpotsRemaining.setMax(Integer.parseInt(contestMasterResponses.get(position).get_TotalStrength()));
        contestsTotalWinners.setText(contestMasterResponses.get(position).get_TotalWinners()+" Winners");
        contestType.setText(contestMasterResponses.get(position).get_ContestType());
        contestButton.setText(contestMasterResponses.get(position).get_EntryFeePoints().equalsIgnoreCase("0") ? "Join" : context.getString(R.string.Rs)+contestMasterResponses.get(position).get_EntryFeePoints());


        return rowView;
    }

    public void animateText(TextView textView) {
        Animation animation1 =
                AnimationUtils.loadAnimation(context,
                        R.anim.blink);
        textView.startAnimation(animation1);
    }




}