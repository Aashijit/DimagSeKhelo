package team.exp.dimagsekhelo.Activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.PlayerEditAdapter;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.WebServiceRequestObjects.PlayerTeamContest;
import team.exp.dimagsekhelo.WebServiceRequestObjects.TeamContestRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.PlayerResponse;

import static team.exp.dimagsekhelo.Utils.Codes.CONTEST_ID;
import static team.exp.dimagsekhelo.Utils.Codes.MATCH_ID;
import static team.exp.dimagsekhelo.Utils.Codes.TEAM;
import static team.exp.dimagsekhelo.Utils.Codes.TEAM_ID;

public class TeamEditScreen extends AppCompatActivity {

    TextView textView;
    ListView listView;

    String contestId, teamId, matchId;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceRoot = database.getReference();
    DatabaseReference databaseReferenceTeams = databaseReferenceRoot.child("TeamContest");
    DatabaseReference databaseReferencePlayerMaster = databaseReferenceRoot.child("PlayerMasterContest");

    TeamContestRequest glTeamContestRequest;


    final List<PlayerResponse> glPlayerResponses = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_edit_screen);

        textView = (TextView) findViewById(R.id.creditsLeft);
        listView = (ListView) findViewById(R.id.listViewTeam);

        contestId = getIntent().getStringExtra(CONTEST_ID);
        teamId = getIntent().getStringExtra(TEAM_ID);
        matchId = getIntent().getStringExtra(MATCH_ID);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching team details");

        progressDialog.show();

        Toast.makeText(getApplicationContext(), contestId + "\t" + teamId + "\t" + matchId, Toast.LENGTH_LONG).show();


        //Fetch the Contest Players
        databaseReferencePlayerMaster.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    PlayerResponse playerResponse = dataSnapshot1.getValue(PlayerResponse.class);


                    if (playerResponse == null)
                        continue;

                    if (playerResponse.get_MatchId().equalsIgnoreCase(matchId)) {
                        glPlayerResponses.add(playerResponse);
                    }
                }

                selectPlayersUsingTeamId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void saveNewTeam(View view) {

        List<PlayerTeamContest> playerTeamContests = new ArrayList<>();

        for (PlayerResponse playerResponse : glPlayerResponses) {

            if (playerResponse.getPlayerSelected() != null)
                if (playerResponse.getPlayerSelected()) {
                    PlayerTeamContest pl = new PlayerTeamContest();
                    pl.set_PlayerId(playerResponse.get_PlayerId());
                    playerTeamContests.add(pl);
                }
        }

        glTeamContestRequest.setPlayers(playerTeamContests);


        databaseReferenceTeams.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TeamContestRequest teamContestRequest = dataSnapshot1.getValue(TeamContestRequest.class);

                    if (teamContestRequest == null)
                        continue;

                    if (teamContestRequest.get_TeamId().equalsIgnoreCase(teamId)) {

                        String key = dataSnapshot1.getKey();

                        databaseReferenceTeams.child(key).setValue(glTeamContestRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "Team Updated !!!", Toast.LENGTH_LONG).show();
                                finish();
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

    private void selectPlayersUsingTeamId() {
        databaseReferenceTeams.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TeamContestRequest teamContestRequest = dataSnapshot1.getValue(TeamContestRequest.class);

                    if (teamContestRequest == null)
                        continue;

                    if (teamContestRequest.get_TeamId().equalsIgnoreCase(teamId)) {
                        glTeamContestRequest = teamContestRequest;
                        Toast.makeText(getApplicationContext(), "Entered ", Toast.LENGTH_LONG).show();
                        updateArrayAdapter();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateArrayAdapter() {
        Double d = 100.00d;
        for (PlayerResponse playerResponse : glPlayerResponses) {
            Log.d("Players", playerResponse.toString());
            if (checkIfPresent(playerResponse.get_PlayerId())) {
                playerResponse.setPlayerSelected(true);
                d = d - Double.parseDouble(playerResponse.get_PlayerCredit());
            }
        }
        textView.setText(d + "");

        PlayerEditAdapter playerEditAdapter = new PlayerEditAdapter(this, glPlayerResponses, textView);
        listView.setAdapter(playerEditAdapter);
        progressDialog.dismiss();
    }

    private boolean checkIfPresent(String playerId) {
        for (PlayerTeamContest playerTeamContest : glTeamContestRequest.getPlayers()) {
            if (playerTeamContest.get_PlayerId().equalsIgnoreCase(playerId))
                return true;
        }
        return false;
    }
}