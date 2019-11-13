package team.exp.dimagsekhelo.CustomUIElements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.Activity.MyTeamActivity;
import team.exp.dimagsekhelo.Activity.TeamEditScreen;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.DateUtils;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceRequestObjects.TeamContestRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.ContestMasterResponse;
import team.exp.dimagsekhelo.WebServiceResponseObjects.MatchStatus;

import static team.exp.dimagsekhelo.Utils.Codes.CONTEST_ID;
import static team.exp.dimagsekhelo.Utils.Codes.MATCH_ID;
import static team.exp.dimagsekhelo.Utils.Codes.TEAM_ID;

public class MyTeamAdapter extends ArrayAdapter<TeamContestRequest> {

    private final Activity context;
    private List<TeamContestRequest> teamContestRequests;
    private DateUtils dateUtils;

    private FirebaseAuth firebaseAuth;
    String glContestId,glTeamId;


    private ProgressDialog progressDialog;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceMyTeams = database.getReference("TeamContest");
    DatabaseReference databaseReferenceContestUser = database.getReference("ContestUser");
    DatabaseReference databaseReferenceContestMaster = database.getReference("ContestMaster");
    DatabaseReference databaseReferenceMatchStatus = database.getReference("MatchStatus");


    public MyTeamAdapter(Activity context, List<TeamContestRequest> teamContestRequests) {
        super(context, R.layout.myteams, teamContestRequests);
        this.context=context;
        this.teamContestRequests = teamContestRequests;

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Checking the match details ...");
        dateUtils = new DateUtils();
    }


    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.myteams, null,true);

        final TextView teamName = (TextView) rowView.findViewById(R.id.matchCaption);
        final ImageButton button = (ImageButton) rowView.findViewById(R.id.editTeam);

        teamName.setText(teamContestRequests.get(position).get_TeamName());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                checkContestUserByTeamId(teamContestRequests.get(position).get_TeamId());
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




    private void checkContestUserByTeamId(final String teamId) {
        glTeamId = teamId;
        databaseReferenceContestUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ContestUserRequest contestUserRequest = dataSnapshot1.getValue(ContestUserRequest.class);

                    if(contestUserRequest == null)
                        continue;


                    if(contestUserRequest.get_TeamId().equalsIgnoreCase(teamId)){
                        checkContestMasterUsingContestId(contestUserRequest.get_ContestId());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void checkContestMasterUsingContestId(final String contestId) {
        glContestId = contestId;
        databaseReferenceContestMaster.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ContestMasterResponse contestMasterResponse = dataSnapshot1.getValue(ContestMasterResponse.class);
                    if(contestMasterResponse == null)
                        continue;
                    if(contestMasterResponse.get_ContestId().equalsIgnoreCase(contestId)){
                        //Check match status using match Id
                        checkMatchStatus(contestMasterResponse.get_MatchId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void checkMatchStatus(final String matchId) {
        databaseReferenceMatchStatus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    MatchStatus matchStatus = dataSnapshot1.getValue(MatchStatus.class);

                    if(matchStatus == null)
                        continue;

                    if(matchStatus.get_MatchId().equalsIgnoreCase(matchId))
                    {
                        progressDialog.dismiss();
                        if(matchStatus.get_MatchStatus().equalsIgnoreCase("Running"))
                        {
                            Toast.makeText(context,"Match is running  !!! Team Edit not possible now !!!!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            //Take the user to the team edit screen
                            Intent intent = new Intent(context, TeamEditScreen.class);
                            intent.putExtra(CONTEST_ID,glContestId);
                            intent.putExtra(TEAM_ID,glTeamId);
                            intent.putExtra(MATCH_ID,matchId);
                            context.startActivity(intent);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}