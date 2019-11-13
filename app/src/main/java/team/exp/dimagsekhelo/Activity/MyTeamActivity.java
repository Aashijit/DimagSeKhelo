package team.exp.dimagsekhelo.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.MyTeamAdapter;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceRequestObjects.TeamContestRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.ContestMasterResponse;
import team.exp.dimagsekhelo.WebServiceResponseObjects.MatchStatus;

import static team.exp.dimagsekhelo.Utils.Codes.CONTEST_ID;
import static team.exp.dimagsekhelo.Utils.Codes.MATCH_ID;
import static team.exp.dimagsekhelo.Utils.Codes.TEAM_ID;

public class MyTeamActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceMyTeams = database.getReference("TeamContest");
    DatabaseReference databaseReferenceContestUser = database.getReference("ContestUser");
    DatabaseReference databaseReferenceContestMaster = database.getReference("ContestMaster");
    DatabaseReference databaseReferenceMatchStatus = database.getReference("MatchStatus");

    private List<TeamContestRequest> teamContestRequests;

    private MyTeamAdapter myTeamAdapter;

    private ListView myTeamList;

    private ProgressDialog progressDialog;

    private String glContestId;
    private String glTeamId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);

        firebaseAuth = FirebaseAuth.getInstance();

        Context context;
        progressDialog = new ProgressDialog(MyTeamActivity.this);

        myTeamList = (ListView) findViewById(R.id.myTeamsList);

        fetchTeams();


        myTeamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //check if the match is running or not
                //fetch the contest user

                Toast.makeText(getApplicationContext(),"Entered  !!!!",Toast.LENGTH_LONG).show();
                progressDialog.show();
                progressDialog.setCancelable(false);
                checkContestUserByTeamId(teamContestRequests.get(i).get_TeamId());
            }
        });

    }

    private void fetchTeams(){
        databaseReferenceMyTeams.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teamContestRequests = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TeamContestRequest teamContestRequest = snapshot.getValue(TeamContestRequest.class);

                    if(teamContestRequest == null)
                        continue;

                    if(teamContestRequest.get_UserPhoneNumber().equalsIgnoreCase(firebaseAuth.getCurrentUser().getPhoneNumber()))
                        teamContestRequests.add(teamContestRequest);

                }
                myTeamAdapter = new MyTeamAdapter(MyTeamActivity.this,teamContestRequests);
                myTeamList.setAdapter(myTeamAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(this.getClass().getName(),"Could not fetch teams !!!");
                return;
            }
        });
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
                      if(matchStatus.get_MatchStatus().equalsIgnoreCase("Running"))
                      {
                          Toast.makeText(getApplicationContext(),"Match is running  !!! Team Edit not possible now !!!!",Toast.LENGTH_SHORT).show();
                          return;
                      }
                      else
                      {
                          //Take the user to the team edit screen
                          Intent intent = new Intent(MyTeamActivity.this,TeamEditScreen.class);
                          intent.putExtra(CONTEST_ID,glContestId);
                          intent.putExtra(TEAM_ID,glTeamId);
                          intent.putExtra(MATCH_ID,matchId);
                          startActivity(intent);
                      }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void myTeamGoBack(View view) {
        finish();
    }
}