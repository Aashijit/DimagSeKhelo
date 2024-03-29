package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.ContestMasterAdapter;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.ContestMasterResponse;

/**
 *This is the contest selection screen where a particular contest needs to be selected
 */
public class ContestSelectionScreen extends AppCompatActivity implements Codes {



    private ListView listView;
    private ProgressBar progressBar;
    private TextView contestSelectionMatchNameTextView;


    private FirebaseAuth firebaseAuth;
    private String matchId;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceContestMaster = database.getReference("ContestMaster");
    DatabaseReference databaseReferenceContestUser = database.getReference("ContestUser");

    private ContestMasterAdapter contestMasterAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_selection_screen);



        listView =  (ListView) findViewById(R.id.contestSelectionListview);
        progressBar = (ProgressBar) findViewById(R.id.progressBarContests);
        contestSelectionMatchNameTextView = (TextView) findViewById(R.id.contestSelectionMatchName);

        //Initialize the variables
        firebaseAuth = FirebaseAuth.getInstance();
        //Initialize the variables



        contestSelectionMatchNameTextView.setText(getIntent().getStringExtra(MATCH_NAME));
        matchId = getIntent().getStringExtra(MATCH_ID);

        //Fetch the upcoming matches here
        progressBar.setVisibility(View.VISIBLE);
        Log.d(this.getClass().getName(),"Progress Bar : "+progressBar.getProgress());
        fetchContests();



    }

    private void fetchContests() {

        Log.d(this.getClass().getName(),"Entered Here ");
        final List<ContestMasterResponse> contestMasterResponses = new ArrayList<>();
        final List<String> joinedContests = new ArrayList<>();
        databaseReferenceContestMaster.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp : dataSnapshot.getChildren()){

                    ContestMasterResponse contestMasterResponse = dsp.getValue(ContestMasterResponse.class);
                    Log.d(this.getClass().getName(),"Entered Here : "+contestMasterResponse);

                    if(contestMasterResponse != null)
                     if(contestMasterResponse.get_MatchId().equalsIgnoreCase(matchId))
                         contestMasterResponses.add(contestMasterResponse);
                }

                //Step # : Fetch the contest user to check the contests the person has been a part of
                databaseReferenceContestUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            for(DataSnapshot dsp : dataSnapshot.getChildren()){

                                ContestUserRequest contestUserRequest = dsp.getValue(ContestUserRequest.class);

                                if(contestUserRequest == null)
                                    return;
                                for(ContestMasterResponse contestMasterResponse : contestMasterResponses) {
                                    if (contestUserRequest.get_ContestId().equalsIgnoreCase(contestMasterResponse.get_ContestId()) && contestUserRequest.get_UserPhoneNumber().equalsIgnoreCase(firebaseAuth.getCurrentUser().getPhoneNumber()))
                                        joinedContests.add(contestUserRequest.get_ContestId());
                                }
                            }
                        }


                        //Step 2 : Update the List View
                        contestMasterAdapter = new ContestMasterAdapter(ContestSelectionScreen.this,contestMasterResponses,joinedContests);
                        listView.setAdapter(contestMasterAdapter);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"No Contests Available !!! ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void contestSelectionGoBack(View view) {
        finish();
    }

    public void contestSelectionCheckPoints(View view) {

    }
}
