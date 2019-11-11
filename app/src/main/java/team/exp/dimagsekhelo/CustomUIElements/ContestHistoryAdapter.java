package team.exp.dimagsekhelo.CustomUIElements;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.exp.dimagsekhelo.DataObject.ContestHistory;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.DateUtils;

public class ContestHistoryAdapter  extends ArrayAdapter<ContestHistory> {

    private final Activity context;
    private List<ContestHistory> contestHistories;
    private DateUtils dateUtils;

    public ContestHistoryAdapter(Activity context, List<ContestHistory> contestHistories) {
        super(context, R.layout.contesthistory, contestHistories);
        this.context=context;
        this.contestHistories = contestHistories;
        dateUtils = new DateUtils();
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.contesthistory, null,true);

        final TextView matchHistoryCaption = (TextView) rowView.findViewById(R.id.matchHistoryCaption);
        final TextView matchHistoryDateTime = (TextView) rowView.findViewById(R.id.matchHistoryDateTime);
        final TextView contestHistoryType = (TextView) rowView.findViewById(R.id.contestHistoryType);
        final TextView points = (TextView) rowView.findViewById(R.id.points);
        final TextView ranks = (TextView) rowView.findViewById(R.id.ranks);
        final ImageButton redeem = (ImageButton) rowView.findViewById(R.id.redeem);

        matchHistoryCaption.setText(contestHistories.get(position).getMatchCaption());
        matchHistoryDateTime.setText(contestHistories.get(position).getMatchTiming());
        contestHistoryType.setText(contestHistories.get(position).getContestType());
        points.setText(contestHistories.get(position).getContestScore());
        ranks.setText(contestHistories.get(position).getContestRank());



        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return rowView;
    }


}
