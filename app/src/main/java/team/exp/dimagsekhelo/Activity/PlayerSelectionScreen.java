package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.ContestMasterAdapter;
import team.exp.dimagsekhelo.CustomUIElements.PlayerAdapter;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.WebServiceResponseObjects.ContestMasterResponse;
import team.exp.dimagsekhelo.WebServiceResponseObjects.PlayerResponse;

public class PlayerSelectionScreen extends AppCompatActivity implements Codes {

    //UI initialized
    TextView textViewCredits;
    ListView listViewPlayers;
    ProgressBar progressBar;


    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferencePlayerMasterContest = database.getReference("PlayerMasterContest");

    private PlayerAdapter playerAdapter;

    private String contestId ;

    private String matchId;
    private String amount;

    List<PlayerResponse> playerResponses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_selection_screen);

        textViewCredits = (TextView)findViewById(R.id.playerSelectionRemainingCredits);
        listViewPlayers = (ListView) findViewById(R.id.playerSelectionList);
        progressBar = (ProgressBar) findViewById(R.id.progressBarCreateTeam);

        contestId = getIntent().getStringExtra(CONTEST_ID);
        matchId = getIntent().getStringExtra(MATCH_ID);
        amount = getIntent().getStringExtra("amount");
        Log.d(this.getClass().getName(),amount +" Player Selection Received");
        playerResponses  = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        fetchPlayersBasedOnMatchId();

    }

    private void fetchPlayersBasedOnMatchId() {

        Log.d(this.getClass().getName(),"Entered Here ");

        databaseReferencePlayerMasterContest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp : dataSnapshot.getChildren()){

                    PlayerResponse playerResponse = dsp.getValue(PlayerResponse.class);
                    Log.d(this.getClass().getName(),"Entered Here : "+playerResponse);

                    if(playerResponse != null)
                        if(playerResponse.get_MatchId().equalsIgnoreCase(matchId))
                            playerResponses.add(playerResponse);
                }
                //Step 2 : Update the List View
                playerAdapter = new PlayerAdapter(PlayerSelectionScreen.this,playerResponses,textViewCredits);
                listViewPlayers.setAdapter(playerAdapter);
                progressBar.setVisibility(View.GONE);


                //Step 3 : Setup the list on click adapter

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"No Contests Available !!! ", Toast.LENGTH_LONG).show();
            }
        });





    }

    public void playerSelectionGoBack(View view){
        finish();
    }


    public void playerSelectionCheckPoints(View view){

    }

    public void createTeam(View view) {

        if(playerResponses == null)
        {
            Toast.makeText(getApplicationContext(),"No players selected !!!",Toast.LENGTH_LONG).show();
            return;
        }

        if(playerResponses.size() == 0)
        {
            Toast.makeText(getApplicationContext(),"No players selected !!!",Toast.LENGTH_LONG).show();
            return;
        }

        if(contestId == null){
            Toast.makeText(getApplicationContext(),"Please select a valid contest !!!",Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(PlayerSelectionScreen.this, CaptainSelectionScreen.class);
        intent.putParcelableArrayListExtra(TEAM, (ArrayList<? extends Parcelable>) playerResponses);
        intent.putExtra(CONTEST_ID,contestId);
        intent.putExtra("amount",amount);
        startActivity(intent);
    }
}
