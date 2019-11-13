package team.exp.dimagsekhelo.CustomUIElements;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import team.exp.dimagsekhelo.Activity.RedeemPointsScreen;
import team.exp.dimagsekhelo.DataObject.ContestHistory;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceResponseObjects.MatchStatus;

import static team.exp.dimagsekhelo.Utils.Codes.CONTEST_ID;
import static team.exp.dimagsekhelo.Utils.Codes.MATCH_ID;

public class ContestHistoryAdapter  extends ArrayAdapter<ContestHistory> {

    private final Activity context;
    private List<ContestHistory> contestHistories;
    private DateUtils dateUtils;
    private DatabaseReference databaseReferenceMatchStatus;

    public ContestHistoryAdapter(Activity context, List<ContestHistory> contestHistories, DatabaseReference databaseReferenceMatchStatus) {
        super(context, R.layout.contesthistory, contestHistories);
        this.context=context;
        this.contestHistories = contestHistories;
        this.databaseReferenceMatchStatus = databaseReferenceMatchStatus;
        dateUtils = new DateUtils();
    }


    public View getView(final int position, View view, ViewGroup parent) {
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
                //Check if the match is running or not
                databaseReferenceMatchStatus.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            MatchStatus matchStatus = dataSnapshot1.getValue(MatchStatus.class);

                            if(matchStatus == null)
                                continue;

                            if(matchStatus.get_MatchId().equalsIgnoreCase(contestHistories.get(position).getMatchId())){
                                if(matchStatus.get_MatchStatus().equalsIgnoreCase("Running")){
                                    Toast.makeText(context,"The match is still running !!!!",Toast.LENGTH_LONG).show();
                                    return;
                                }else {
                                    Intent intent = new Intent(context, RedeemPointsScreen.class);
                                    intent.putExtra(MATCH_ID,contestHistories.get(position).getMatchId());
                                    intent.putExtra(CONTEST_ID,contestHistories.get(position).getContestId());
                                    context.startActivity(intent);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return rowView;
    }
}