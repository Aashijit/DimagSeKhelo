package team.exp.dimagsekhelo.CustomUIElements;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import team.exp.dimagsekhelo.Database.BusinessLogic.PointGenerationSystem;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.CurrentMatchPoints;


public class CurrentMatchAdapter extends ArrayAdapter<CurrentMatchPoints> implements Codes {

    private final Activity context;
    private List<CurrentMatchPoints> currentMatchPointsList;
    private DatabaseReference databaseReferenceContestUser;
    private String matchType;
    private PointGenerationSystem pointGenerationSystem;
    private FirebaseUser firebaseUser;
    private TextView textView;
    private Double totalPoints = 0.00d;
    private String captainId, viceCaptainId;

    private List<ContestUserRequest> contestUserRequests;
    private String contestId;

    public CurrentMatchAdapter(Activity context, List<CurrentMatchPoints> currentMatchPointsList,String matchType, TextView textView, DatabaseReference databaseReferenceContestUser, FirebaseUser firebaseUser, String captainId,String viceCaptainId) {
        super(context, R.layout.point, currentMatchPointsList);
        this.context = context;
        this.matchType = matchType;
        this.currentMatchPointsList = currentMatchPointsList;
        this.textView = textView;
        pointGenerationSystem = new PointGenerationSystem();
        this.databaseReferenceContestUser = databaseReferenceContestUser;
        this.firebaseUser = firebaseUser;
        this.captainId = captainId;
        this.viceCaptainId = viceCaptainId;
        contestUserRequests = new ArrayList<ContestUserRequest>();
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
            points = pointGenerationSystem.getPointsForODI(currentMatchPoints,captainId,viceCaptainId);
        else if(matchType.equalsIgnoreCase(MATCH_TYPE_T20))
            points = pointGenerationSystem.getPointsForODI(currentMatchPoints,captainId,viceCaptainId);

        pointsEarned.setText(points+"");
        currentMatchPoints.setPoints(points);


        totalPoints = 0.00d;

        for(CurrentMatchPoints matchPoints : currentMatchPointsList){
            if(matchPoints != null && matchPoints.getPoints() != null)
                totalPoints += matchPoints.getPoints();
        }

        textView.setText("Total Points Earned : "+totalPoints+"");

        contestId = currentMatchPoints.get_ContestId();


        //Update the total Points
        //Fetch all the Contest Users and also calculate the rank

        databaseReferenceContestUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ContestUserRequest contestUserRequest = dataSnapshot1.getValue(ContestUserRequest.class);

                    if(contestUserRequest == null)
                        continue;

                    if(contestUserRequest.get_UserPhoneNumber().equalsIgnoreCase(firebaseUser.getPhoneNumber()) && contestUserRequest.get_ContestId().equalsIgnoreCase(currentMatchPointsList.get(position).get_ContestId())){
                       String key = dataSnapshot1.getKey();

                       if(key == null)
                           continue;
                       //Update the field now
                        databaseReferenceContestUser.child(key).child("_Points").setValue(totalPoints+"").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                  if(task.isSuccessful()){
                                      Toast.makeText(context,"Points updated !!!",Toast.LENGTH_SHORT).show();
                                      //update the ranks by fetching all the contest user records
                                      updateRanks();
                                }
                            }
                        });
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Points not updated !!!",Toast.LENGTH_SHORT).show();
            }
        });
        return rowView;
    }

    private void updateRanks() {
        databaseReferenceContestUser.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ContestUserRequest contestUserRequest = dataSnapshot1.getValue(ContestUserRequest.class);

                    if(contestUserRequest == null)
                        continue;

                    if(contestUserRequest.get_ContestId().equalsIgnoreCase(contestId) && contestUserRequest.get_Points().equalsIgnoreCase("0"))
                        contestUserRequests.add(contestUserRequest);
                }
                //Update the ranks
                changeRanks(contestUserRequests);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){
                Toast.makeText(context,"Error occurred !!!",Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     *
     * @param contestUserRequests
     */
    private void changeRanks(@NonNull List<ContestUserRequest> contestUserRequests){
        Collections.sort(contestUserRequests, new Comparator<ContestUserRequest>(){
            @Override
            public int compare(ContestUserRequest o1, ContestUserRequest o2) {

                double crit1,crit2;

                double cur1 = Double.parseDouble(o1.get_Points());
                double cur2 = Double.parseDouble(o2.get_Points());

                crit1 = cur1;
                crit2 = cur2;

                String timestamp1= o1.get_ContestJoinTimeStamp();
                String timestamp2= o2.get_ContestJoinTimeStamp();

                Timestamp tst1 = Timestamp.valueOf(timestamp1);
                Timestamp tst2 = Timestamp.valueOf(timestamp2);

                if(cur1 == cur2){
                    crit1 = tst1.getTime();
                    crit2 = tst2.getTime();
                }

                return (int)(crit1 - crit2);
            }
        });

        //Add the ranks to each and every Player with Score more than 0
        int rnk=1;
        for(ContestUserRequest contestUserRequest  : contestUserRequests){
            contestUserRequest.set_Rank(rnk+"");
            rnk++;
        }


        //Update the ranks

    }

    public void animateImage(@NonNull ImageView imageView) {
        Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.blink);
        imageView.startAnimation(animation1);
    }
}