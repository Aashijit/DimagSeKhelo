package team.exp.dimagsekhelo.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.CurrentMatchAdapter;
import team.exp.dimagsekhelo.CustomUIElements.PlayerAdapter;
import team.exp.dimagsekhelo.Database.BusinessLogic.StringDeserializer;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceRequestObjects.PlayerTeamContest;
import team.exp.dimagsekhelo.WebServiceRequestObjects.TeamContestRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.CurrentMatchPoints;
import team.exp.dimagsekhelo.WebServiceResponseObjects.PlayerResponse;
import team.exp.dimagsekhelo.WebServiceResponseObjects.WicketsTaken;

import static team.exp.dimagsekhelo.Utils.Codes.CONTEST_ID;

public class PointChangeScreen extends AppCompatActivity {


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceCurrentMatches = database.getReference("CurrentMatches");
    DatabaseReference databaseReferenceContestUser = database.getReference("ContestUser");
    DatabaseReference databaseReferenceTeamContest = database.getReference("TeamContest");

    private CurrentMatchAdapter currentMatchAdapter;

    private TextView textViewPointChangeScreenTextView;
    private ListView pointSystemList;
    private FirebaseAuth firebaseAuth;

    private TeamContestRequest finalTeamContestRequest;

    private String contestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_change_screen);

        firebaseAuth = FirebaseAuth.getInstance();

        textViewPointChangeScreenTextView = (TextView)  findViewById(R.id.totalPointsEarned);
        pointSystemList = (ListView) findViewById(R.id.pointSystemList);
        contestId = getIntent().getStringExtra(CONTEST_ID);



        databaseReferenceContestUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    final ContestUserRequest contestUserRequest = dataSnapshot1.getValue(ContestUserRequest.class);
                    if(contestUserRequest == null)
                        continue;

                    if(contestUserRequest.get_ContestId().equalsIgnoreCase(contestId) && contestUserRequest.get_UserPhoneNumber().equalsIgnoreCase(firebaseAuth.getCurrentUser().getPhoneNumber())){
                        databaseReferenceTeamContest.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()){
                                    TeamContestRequest teamContestRequest = dataSnapshot2.getValue(TeamContestRequest.class);
                                    if(teamContestRequest == null)
                                        continue;

                                    if(teamContestRequest.get_TeamId().equalsIgnoreCase(contestUserRequest.get_TeamId())){

                                        Log.d(this.getClass().getSimpleName(),"Team Fetched"+teamContestRequest.toString());
                                        finalTeamContestRequest = teamContestRequest;
                                        pointChangeStart();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void pointChangeStart() {

        Log.d(this.getClass().getName(),"Point Change Started" + finalTeamContestRequest.toString());

        databaseReferenceCurrentMatches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<CurrentMatchPoints> currentMatchPoints = new ArrayList<>();
                Log.d(this.getClass().getName(),dataSnapshot.getChildrenCount()+"");
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    CurrentMatchPoints currentMatchPoints1 = snapshot.getValue(CurrentMatchPoints.class);

                    if(currentMatchPoints1 == null)
                        return;

                    if(currentMatchPoints1.get_ContestId().equalsIgnoreCase(contestId) && containsPlayer(currentMatchPoints1.get_PlayerId())) {

                        currentMatchPoints.add(currentMatchPoints1);
                    }
                }

                currentMatchAdapter=  new CurrentMatchAdapter(PointChangeScreen.this,currentMatchPoints, Codes.MATCH_TYPE_ODI,textViewPointChangeScreenTextView,databaseReferenceContestUser,firebaseAuth.getCurrentUser());
                pointSystemList.setAdapter(currentMatchAdapter);

              }
                //Step 2 : Update the List View


                //Step 3 : Setup the list on click adapter



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"No Contests Available !!! ", Toast.LENGTH_LONG).show();
            }
        });





    }


    private boolean containsPlayer(String playerId){

        if(finalTeamContestRequest.get_CaptainPlayerId().equalsIgnoreCase(playerId))
            return true;

        if(finalTeamContestRequest.get_ViceCaptainPlayerId().equalsIgnoreCase(playerId))
            return true;


        for(PlayerTeamContest playerTeamContest : finalTeamContestRequest.getPlayers()){
            if(playerTeamContest.get_PlayerId().equalsIgnoreCase(playerId))
                return true;
        }
        return false;
    }

    public void pointsGoBack(View view) {
        finish();
    }



}
