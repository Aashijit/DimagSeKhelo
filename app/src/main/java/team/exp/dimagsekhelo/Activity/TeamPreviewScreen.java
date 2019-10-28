package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.TeamPreviewAdapter;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceRequestObjects.PlayerTeamContest;
import team.exp.dimagsekhelo.WebServiceRequestObjects.TeamContestRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.PlayerResponse;

public class TeamPreviewScreen extends AppCompatActivity  implements Codes {

    private ListView teamPreviewList;
    private TeamPreviewAdapter teamPreviewAdapter;

    private List<PlayerResponse> playerResponseList;

    private Button joinContestButton;

    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceRoot;
    private DatabaseReference databaseReferenceTeamContest;
    private DatabaseReference databaseReferenceContestUser;

    private String contestId;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_preview_screen);

        teamPreviewList = (ListView) findViewById(R.id.teamPreviewList);
        joinContestButton = (Button) findViewById(R.id.joinContestButton);

        firebaseAuth = FirebaseAuth.getInstance();

        contestId = getIntent().getStringExtra(CONTEST_ID);
        amount = getIntent().getStringExtra("amount");

        if(getIntent().getStringExtra("OnlyView") != null)
        {
            joinContestButton.setVisibility(View.GONE);
        }



        if(contestId == null){
            Toast.makeText(getApplicationContext(),"Please select a valid Contest !!!",Toast.LENGTH_LONG).show();
            return;
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceRoot = firebaseDatabase.getReference();
        databaseReferenceTeamContest = databaseReferenceRoot.child("TeamContest");
        databaseReferenceContestUser=  databaseReferenceRoot.child("ContestUser");


        playerResponseList = getIntent().getParcelableArrayListExtra(TEAM);


        List<PlayerResponse> finalPlayerResponses = new ArrayList<>();

        for(PlayerResponse playerResponse : playerResponseList){
            Log.d(this.getClass().getName(),playerResponse.toString());
            if(playerResponse.getPlayerSelected() != null)
                if(playerResponse.getPlayerSelected())
                    finalPlayerResponses.add(playerResponse);
        }

        //Paint the screen
        teamPreviewAdapter = new TeamPreviewAdapter(this, finalPlayerResponses);
        teamPreviewList.setAdapter(teamPreviewAdapter);

    }

    public void teamPreviewGoBack(View view) {
        finish();
    }

    public void joinContest(View view) {



        //Step 1 : Insert a row in the team contest json
        String captainPlayerId ="";
        String viceCaptainPlayerId ="";
        List<PlayerTeamContest> playerTeamContests = new ArrayList<>();
        for(PlayerResponse playerResponse : playerResponseList){
            PlayerTeamContest playerTeamContest  = new PlayerTeamContest();
            if(playerResponse.getPlayerCaptain() != null && playerResponse.getPlayerCaptain()) {
                captainPlayerId = playerResponse.get_PlayerId();
                continue;
            }
            if(playerResponse.getPlayerViceCaptain() != null && playerResponse.getPlayerViceCaptain()) {
                viceCaptainPlayerId = playerResponse.get_PlayerId();
                continue;
            }
            playerTeamContest.set_PlayerId(playerResponse.get_PlayerId());
            playerTeamContests.add(playerTeamContest);
        }

        TeamContestRequest teamContestRequest = new TeamContestRequest();
        final String teamId = new Date().getTime()+"";
        teamContestRequest.set_TeamId(teamId);
        teamContestRequest.set_IsDelete("0");
        teamContestRequest.set_CaptainPlayerId(captainPlayerId);
        teamContestRequest.set_ViceCaptainPlayerId(viceCaptainPlayerId);
        teamContestRequest.set_UserPhoneNumber(firebaseAuth.getCurrentUser().getPhoneNumber());
        teamContestRequest.setPlayers(playerTeamContests);
        teamContestRequest.set_TeamName("DSK Team");

        String pushId = databaseReferenceTeamContest.push().getKey();


        if(pushId == null){
            Toast.makeText(getApplicationContext(),"Contest Joining Failed !!!!",Toast.LENGTH_LONG).show();
            return;
        }

        databaseReferenceTeamContest.child(pushId).setValue(teamContestRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    ContestUserRequest contestUserRequest = new ContestUserRequest();
                    contestUserRequest.set_ContestId(contestId);
                    contestUserRequest.set_TeamId(teamId);
                    contestUserRequest.set_UserPhoneNumber(firebaseAuth.getCurrentUser().getPhoneNumber());
                    Log.v(this.getClass().getSimpleName(),"Saving contest : "+contestUserRequest.toString());
                    addContest(contestUserRequest);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Contest Joining Failed !!!!",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });



    }


    private void addContest(ContestUserRequest contestUserRequest){

        String pushId = databaseReferenceContestUser.push().getKey();

        if(pushId == null)
        {
            Toast.makeText(getApplicationContext(),"Contest Adding Failed !!!!",Toast.LENGTH_LONG).show();
            return;
        }

        databaseReferenceContestUser.child(pushId).setValue(contestUserRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Log.d(this.getClass().getName(),amount+" Received");
                    //Check if amount is present or null
                    if(amount != null) {
                        Intent intent = new Intent(TeamPreviewScreen.this, PaymentScreen.class);
                        intent.putExtra("amount",amount);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(TeamPreviewScreen.this, HomeScreen.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
