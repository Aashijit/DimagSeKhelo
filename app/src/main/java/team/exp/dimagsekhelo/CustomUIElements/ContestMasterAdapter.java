package team.exp.dimagsekhelo.CustomUIElements;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import team.exp.dimagsekhelo.Activity.ContestSelectionScreen;
import team.exp.dimagsekhelo.Activity.PaymentScreen;
import team.exp.dimagsekhelo.Activity.PlayerSelectionScreen;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceResponseObjects.ContestMasterResponse;

import static team.exp.dimagsekhelo.Utils.Codes.CONTEST_ID;

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


    public View getView(final int position, View view, ViewGroup parent) {
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

        contestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Transfer the control from contest to Player Selection page
                //Check if the entry fee is more than 0
                if(!contestMasterResponses.get(position).get_EntryFeePoints().equalsIgnoreCase("0"))
                {
                    Intent intent = new Intent(context, PaymentScreen.class);
                    intent.putExtra(CONTEST_ID,contestMasterResponses.get(position).get_ContestId());
                    intent.putExtra("amount",contestMasterResponses.get(position).get_EntryFeePoints());
                    context.startActivity(intent);
                }else {

                    Intent intent = new Intent(context, PlayerSelectionScreen.class);
                    intent.putExtra(CONTEST_ID, contestMasterResponses.get(position).get_ContestId());
                    context.startActivity(intent);
                }
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