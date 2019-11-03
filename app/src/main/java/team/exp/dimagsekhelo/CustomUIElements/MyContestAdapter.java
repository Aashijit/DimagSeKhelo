package team.exp.dimagsekhelo.CustomUIElements;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import team.exp.dimagsekhelo.Activity.PointChangeScreen;
import team.exp.dimagsekhelo.Activity.SplashScreen;
import team.exp.dimagsekhelo.Activity.TeamPreviewScreen;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceRequestObjects.PlayerTeamContest;
import team.exp.dimagsekhelo.WebServiceRequestObjects.TeamContestRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.ContestMasterResponse;
import team.exp.dimagsekhelo.WebServiceResponseObjects.MatchStatus;
import team.exp.dimagsekhelo.WebServiceResponseObjects.PlayerResponse;

import static team.exp.dimagsekhelo.Utils.Codes.CONTEST_ID;
import static team.exp.dimagsekhelo.Utils.Codes.MATCH_ID;
import static team.exp.dimagsekhelo.Utils.Codes.TEAM;


public class MyContestAdapter extends ArrayAdapter<ContestMasterResponse> {


    private final Activity context;
    private List<ContestMasterResponse> contestMasterResponses;
    private List<ContestUserRequest> contestUserRequests;
    private DateUtils dateUtils;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferencePlayerMaster = database.getReference("PlayerMasterContest");
    DatabaseReference databaseReferenceTeamContest = database.getReference("TeamContest");
    DatabaseReference databaseReferenceTeamMatchStatus = database.getReference("MatchStatus");



    public MyContestAdapter(Activity context, List<ContestMasterResponse> contestMasterResponses, List<ContestUserRequest> contestUserRequests)  {
        super(context, R.layout.contest, contestMasterResponses);
        this.context=context;
        this.contestMasterResponses = contestMasterResponses;
        this.contestUserRequests = contestUserRequests;
        dateUtils = new DateUtils();
    }


    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mycontest, null,true);

        final TextView contestPrizePool = (TextView) rowView.findViewById(R.id.myContestPrizePool);
        final TextView contestTotalSpots = (TextView) rowView.findViewById(R.id.myContestTotalSpots);
        final TextView contestSpotsRemaining = (TextView) rowView.findViewById(R.id.myContestSpotsRemaining);
        final ProgressBar progressBarSpotsRemaining = (ProgressBar) rowView.findViewById(R.id.myContestSpotsRemainingBar);
        final TextView contestsTotalWinners = (TextView) rowView.findViewById(R.id.myContestTotalWinnersText);
        final TextView contestType = (TextView) rowView.findViewById(R.id.myContestType);
        final Button contestButton = (Button) rowView.findViewById(R.id.checkTeams);
        final Button checkUpdateButton = (Button) rowView.findViewById(R.id.checkUpdateButton);


        contestPrizePool.setText(context.getString(R.string.Rs)+contestMasterResponses.get(position).get_PrizePool());
        contestTotalSpots.setText(contestMasterResponses.get(position).get_TotalStrength()+" Spots");
        progressBarSpotsRemaining.setMax(Integer.parseInt(contestMasterResponses.get(position).get_TotalStrength()));
        contestsTotalWinners.setText(contestMasterResponses.get(position).get_TotalWinners()+" Winners");
        contestType.setText(contestMasterResponses.get(position).get_ContestType());

        databaseReferenceTeamMatchStatus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    MatchStatus matchStatus = dataSnapshot1.getValue(MatchStatus.class);
                    if(matchStatus == null)
                        return;
                    if(matchStatus.get_MatchId().equals(contestMasterResponses.get(position).get_MatchId())){
                        if(matchStatus.get_MatchStatus().equalsIgnoreCase("Running")) {
                            checkUpdateButton.setEnabled(true);
                            animateButton(checkUpdateButton);
                        } else if(matchStatus.get_MatchStatus().equalsIgnoreCase("Ended")){
                            checkUpdateButton.setText("Check Points");
                            checkUpdateButton.setEnabled(true);
                            animateButton(checkUpdateButton);
                        }

                            checkUpdateButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(checkUpdateButton.getText().toString().equalsIgnoreCase("Check Update")) {

                                        Intent intent = new Intent(context, PointChangeScreen.class);
                                        intent.putExtra(MATCH_ID, contestMasterResponses.get(position).get_MatchId());
                                        intent.putExtra(CONTEST_ID, contestMasterResponses.get(position).get_ContestId());
                                        context.startActivity(intent);
                                    } else if(checkUpdateButton.getText().toString().equalsIgnoreCase("Check Points")){

                                        Intent intent = new Intent(context, PointChangeScreen.class);
                                        intent.putExtra(MATCH_ID, contestMasterResponses.get(position).get_MatchId());
                                        intent.putExtra(CONTEST_ID, contestMasterResponses.get(position).get_ContestId());
                                        context.startActivity(intent);
                                    }
                                }
                            });
                            break;

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //Fetch Team Details
        contestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fetch the Player Master
                databaseReferencePlayerMaster.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final List<PlayerResponse> playerResponses = new ArrayList<>();
                        for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                            PlayerResponse playerResponse = dataSnap.getValue(PlayerResponse.class);
                            playerResponses.add(playerResponse);
                        }

                        databaseReferenceTeamContest.addListenerForSingleValueEvent(new ValueEventListener() {
                            TeamContestRequest teamContestRequestFinal;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnap : dataSnapshot.getChildren()){

                                    TeamContestRequest teamContestRequest = dataSnap.getValue(TeamContestRequest.class);

                                    String teamId = getTeamIdByContestId(contestMasterResponses.get(position).get_ContestId());

                                    Log.v(this.getClass().getName(),"Team Id : "+teamId);
                                    if(teamContestRequest.get_TeamId() != null)
                                        if(teamContestRequest.get_TeamId().equalsIgnoreCase(teamId))
                                            teamContestRequestFinal = teamContestRequest;
                                }

                                List<PlayerResponse> filteredPlayerResponseList = new ArrayList<>();

                                PlayerResponse playerResponse = getPlayerResponseByPlayerId(teamContestRequestFinal.get_CaptainPlayerId(),playerResponses);
                                playerResponse.setPlayerCaptain(true);
                                filteredPlayerResponseList.add(playerResponse);

                                playerResponse = getPlayerResponseByPlayerId(teamContestRequestFinal.get_ViceCaptainPlayerId(),playerResponses);
                                playerResponse.setPlayerViceCaptain(true);
                                filteredPlayerResponseList.add(playerResponse);



                                for(PlayerTeamContest p : teamContestRequestFinal.getPlayers()){
                                    playerResponse = getPlayerResponseByPlayerId(p.get_PlayerId(),playerResponses);
                                    filteredPlayerResponseList.add(playerResponse);
                                }



                                //Transfer Control to the Team Preview Screen
                                Intent intent = new Intent(context, TeamPreviewScreen.class);
                                intent.putParcelableArrayListExtra(TEAM, (ArrayList<? extends Parcelable>) filteredPlayerResponseList);
                                intent.putExtra(CONTEST_ID,contestMasterResponses.get(position).get_ContestId());
                                intent.putExtra("OnlyView","True");
                                context.startActivity(intent);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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

    public void animateButton(Button button) {
        Animation animation1 =
                AnimationUtils.loadAnimation(context,
                        R.anim.blink);
        button.startAnimation(animation1);
    }



    public String getTeamIdByContestId(String contestId){
        for(ContestUserRequest contestUserRequest : contestUserRequests){
            if(contestUserRequest.get_ContestId().equalsIgnoreCase(contestId))
                return contestUserRequest.get_TeamId();
        }
        return null;
    }


    public PlayerResponse getPlayerResponseByPlayerId(String playerId,List<PlayerResponse> playerResponses){
        for(PlayerResponse playerResponse : playerResponses){
            if(playerResponse.get_PlayerId().equalsIgnoreCase(playerId))
                return playerResponse;
        }
        return null;
    }
}